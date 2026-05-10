# Sudoku Application

A command-line Sudoku game built with Java and Spring Boot, featuring a complete validation engine for rows, columns, and 3x3 boxes.

## Technologies Used

- **Java**: Core programming language
- **Spring Boot**: Framework for building the application
- **Maven**: Build and dependency management tool

## Prerequisites

- **OpenJDK 26** or later
- **Maven** (version 3.6 or higher)

## How to Run

1. Clone the repository:
   ```
   git clone <repository-url>
   cd sudoku
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start a command-line interface where you can play Sudoku by setting values, checking validity, and using hints.

## Features

- Set values in Sudoku cells
- Validate moves against Sudoku rules (no duplicates in rows, columns, or 3x3 boxes)
- Clear values
- Hint system for assistance
- Check command to validate the entire board
- Quit command to exit the game

## Project Structure

- `src/main/java/com/flc/org/sudoku/`: Main application code
  - `engine/`: Core Sudoku logic and validation
  - `command/`: Command implementations for user interactions
  - `dispatcher/`: Command dispatching logic
- `src/test/`: Unit tests

## Interview Process Disclaimer

This project was developed as part of an interview process. The focus was on demonstrating clean code design, separation of concerns, and maintainable architecture rather than optimizing for algorithmic efficiency. The validation engine uses a straightforward approach with arrays of ValidationGroup objects to track conflicts in rows, columns, and boxes.

If given more time, a more advanced implementation using Algorithm X and Dancing Links could be explored for puzzle generation and solving, but that would significantly increase complexity and was not feasible within the weekend timeframe.

## AI Usage

- **IDE Suggestions**: AI-powered type-ahead suggestions were enabled in the IDE, providing minor code completion assistance during development. However, no AI intervention occurred in the overall design or architectural decisions.
- **Documentation**: The language in this README and other documentation was polished using AI tools to ensure clarity and professionalism.

## License

This project is for demonstration purposes only.
