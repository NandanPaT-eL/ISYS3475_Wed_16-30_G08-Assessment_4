# StudentScholar Assessment

A Java project for the StudentScholar website, built for the Software Engineering Fundamentals course at RMIT University.

## About

This project implements and tests the `createNewAssessment` function in the `Course` class. The function validates assessment details (name, description, points, submission type, grouping, and dates) against 11 conditions. If everything is valid, it creates an `Assessment` object and stores it in the course's assessment list. Otherwise it returns `false` and changes nothing.

## Project Structure

- `src/course/Assessment.java`: the Assessment class and its constructor
- `src/course/Course.java`: the Course class with `createNewAssessment` and the validator methods
- `test/course/CourseTest.java`: JUnit 5 test cases for all conditions

## Tools

- Java (JDK 17+)
- JUnit 5
- IntelliJ IDEA
- Git and GitHub

## Team

- Nandan Patel (S4253850)
- Ayush Dadhaniya
- Harshal Pawar (S4207724)

Each member is responsible for testing and implementing a set of conditions. Contributions are marked with `// Author:` comments in the code.

## How to Run the Tests

1. Open the project in IntelliJ IDEA (or Eclipse).
2. Make sure JUnit 5 is on the classpath.
3. Right-click `CourseTest.java` and choose **Run 'CourseTest'**.
