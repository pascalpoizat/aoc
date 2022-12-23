# Advent of Code

This repository is used to play with Advent of Code quizzes (several editions).

- run all tests with `./gradlew check jacocoTestReport` (can be VERY long)
- more probably, run a selected test suite with `./gradlew test --tests aoc.aocYYYY.TestSuite jacocoTestReport` where `YYYY` is the year (e.g., 2015)
- run a day with `./gradlew run --args="YYYY Ds"` where `YYYY` is the year (e.g., 2015), `D` is the day (e.g., 1) and `s` is the sub-question (`a` or `b`).
