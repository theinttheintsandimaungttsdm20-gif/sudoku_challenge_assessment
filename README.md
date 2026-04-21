# Sudoku Challenge — Command Line Game

## Overview

A command-line Sudoku game written in pure Java SE 8.
No external libraries are used except JUnit 1.12.x for testing.
The application supports puzzle display, user interaction,
validation, hints, and game completion detection.

---

## Environment

- **Language**: Java SE 8
- **IDE**: Eclipse IDE
- **Build**: No build tool required — plain Java SE
- **OS**: Windows / Linux / macOS
- **Testing**: JUnit 1.12.0

---

## How to Run

### In Eclipse
1. Import project into Eclipse
2. Navigate to `Main.java`
3. Right click -> `Run As` -> `Java Application`
4. For running test -> Navigate to test package and go to the test class -> `Run As JUnit test`

### In Command Line
```bash
# Compile
javac -cp . src/com/sudoku/**/*.java

# Run
java -cp . com.sudoku.Main
```

---

## How to Play
1   2   3   4   5   6   7   8   9
+-------+-------+-------+
A | 5   3   _   | _   7   _   | _   _   _   |
B | 6   _   _   | 1   9   5   | _   _   _   |
...
Enter command (e.g., A3 4, C5 clear, hint, check):

| Command      | Description                        |
|--------------|------------------------------------|
| `B3 7`       | Place number 7 in row B, column 3  |
| `C5 clear`   | Clear cell C5                      |
| `hint`       | Reveal one correct number          |
| `check`      | Validate current board             |
| `quit`       | Exit the game                      |

- **Rows** are labeled A to I (top to bottom)
- **Columns** are labeled 1 to 9 (left to right)
- **Pre-filled cells** cannot be modified
- **Game ends** when all cells are correctly filled

---

## Project Structure
src/
└── com/sudoku/
├── Main.java                    - entry point
├── config/
│   ├── SudokuGameConfig.java     - game configuration by wiring service and controller
├── constant/
│   ├── SudokuConstant.java     - all constants
│   └── ResultMessage.java      - all result message templates
├── model/
│   ├── Board.java              - 9x9 grid state
│   ├── Cell.java               - single cell (value + prefilled)
│   ├── Command.java            - parsed user command
│   └── ValidationResult.java  - validation outcome wrapper
├── helper/
│   └── RowMappingHelper.java   - row index to label mapping
├── service/
│   ├── ValidationService.java  - all validation logic and give validation result
│   ├── HintService.java        - hint logic and give validation result
│   ├── PuzzleService.java      - puzzle and solution provider
│   └── CommandService.java      - parses raw input to Command and check invalid command
├── controller/
│   └── SudokuGameController.java ← middleware to provide service upon receiving input
├── view/
│   └── SudokuGameView.java     ← display console and collect input
└── test/
├── SudokuValidationTest.java  ← validation service tests
├── HintServiceTest.java       ← hint service tests
└── CommandServiceTest.java     ← command service tests

---

## Design Choices

### MVC + Service Pattern

The application follows a clean **MVC + Service** architecture:
*View*       - reads user input, displays output only
*Controller*  - receives input, calls service, returns Result
*Service*     - handles all business logic and validation
*Model*       - constructs pure data, no logic, no display

This separation ensures each class has a **single responsibility**
and the codebase remains easy to maintain and extend.

### Why MVC + Service?

- **View** never contains logic. It has only `System.out` and `Scanner`
- **Controller** never formats messages. Returns structured `Result` - Sucess, Failure, Quit or GameOver
- **Service** never displays — returns `ValidationResult`
- **Model** never validates — pure data with getters and setters

## Application Wiring

`Main` owns the play-again loop and a single shared `Scanner` instance, passed into `SudokuGameView` to avoid conflicting `System.in` readers.`SudokuGameConfig` wires all dependencies cleanly, creating a fresh board, services and controller per game while the same `Scanner` is reused. When a game ends, the user is prompted to press ENTER for a new game or type 'quit' to exit — each new game gets a completely fresh state.

### Dependency Injection via Constructor Injection
By using Constructor Injection, I ensure the Controller depends on the abstraction (interface) rather than the implementation. 
Initializing it in the constructor avoids repeated object construction and ensures the dependency is available and immutable throughout the Controller's lifecycle.

### Validation Design

Validation is split into two layers:
Hard validation  -> throws IllegalArgumentException
-> board structure (null, wrong size)
-> called once at startup
Soft validation  -> returns ValidationResult
-> character checks, rule violations and give invalid message to user. If success, just skip and move to another
-> called on every user move

This prevents the game from crashing during user interaction
while still enforcing strict board structure at startup.

### Why `ValidationResult` Instead of `boolean`?

I chose this approach to encapsulate both the result and the message, providing a cleaner output that eliminates the need for extra boolean checks or separate error handling.
`ValidationResult` wraps:
- `isValid()` — pass or fail
- `getMessage()` — human-readable reason

This allows the controller to pass the exact failure reason
to the view without any logic in between.

### Why `ResultMessage` Enum?

All user-facing messages are centralised in one enum.
This avoids magic strings scattered across the codebase
and makes message changes a single-point update.

```java
ResultMessage.INVALID_ROW.format(duplicate, rowLabel)
// -> "Number 5 is already existed in Row A"
```

### Pre-filled Cell Protection

Pre-filled cells are protected at the `Cell` level.
`Cell.setValue()` returns `false` if the cell is pre-filled
instead of throwing an exception — keeping the game running
and letting the controller show a friendly message.
// -> "Invalid move. %s%d is pre-filled."

### Validate Before Place

Every user move is validated against a **copy of the board**
before being applied to the real board:
propose move -> copy board -> validate copy -> place if valid

This prevents invalid moves from corrupting the board state.

### `PuzzleService` immutableCopy

`PuzzleService` always returns a **immutable copy** of the puzzle
and solution arrays. This protects the internal state from
being mutated by callers.

### `RowMappingHelper`

A dedicated helper class converts between user-facing
coordinates and internal array indices:
'A' -> 0,  'B' -> 1  ...  'I' -> 8

This keeps the conversion logic in one place
and out of the model and service layers.

### checkRuleViolation method

One-Pass Optimal — The “Do it all at once” Method to reduce time complexity O(81) while brute force has time complexity O(243).
Use HashSet to keep seen, if seen has duplicate row, col or box give `ValidationResult` fail message. 
If not have duplicate, add key to seen e.g. e.g. row2:3, col3:3, box01:3

## Testing Approach

The project follows **Test-Driven Development (TDD)**.
Tests were written before implementation for all service
and model classes.

### Test Coverage

| Test Class               | What It Tests                        |
|--------------------------|--------------------------------------|
| `SudokuValidationTest`   | Board structure, duplicates, chars   |
| `HintServiceTest`        | Hint reveal, correct value, no hint  |
| `CommandParserTest`      | Place, clear, hint, invalid inputs   |

### TDD Cycle Followed
Red   -> write failing test
Green -> write minimum code to pass
Blue  -> refactor, keep tests green

### Key Test Decisions

- Static hardcoded boards used — no UI involved in tests
- `SudokuTestBoards` class holds all reusable board fixtures
- Structure failures use `assertThrows` — service returns `ValidationResult`
- Each test has one clear purpose and a descriptive name

---

## Assumptions

- Puzzle is hardcoded with 30 pre-filled numbers
- Solution is known at startup and used for hint reveals
- Input is case-insensitive — `b3 7` and `B3 7` both work
- Empty cells are represented by `'_'`
- Only characters `'1'`-`'9'` and `'_'` are valid cell values
- Pre-filled cells cannot be modified or cleared
- Duplicate detection runs on every place command
  before the value is applied to the board

---

## SOLID Principles Applied

| Principle              | How Applied                                      |
|------------------------|--------------------------------------------------|
| Single Responsibility  | Each class has one clear job                     |
| Open/Closed            | New validators added without changing existing   |
| Liskov Substitution    | `ValidationResult` used consistently throughout |
| Interface Segregation  | Small focused classes, no bloated interfaces     |
| Dependency Inversion   | Controller depends on service, not implementation|
