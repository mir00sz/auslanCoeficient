# AuslanCoefficient

The project is a tool that was supposed to help me and my friend to analyze LSTM neural network
recognizing Australian Sign Language (aka Auslan).

The network's input is a csv-like file containing sensor readings from experimentator's hands.
More about the data you can find <a href="https://archive.ics.uci.edu/ml/datasets/Australian+Sign+Language+signs+(High+Quality)">here</a>.


When the network was trained we created a visualization presenting distances between words
on network's output on each time step.

Finally we wanted to compare those distances with a correlation coefficients between every channel.

The input for the application is a String representing a path to folder that contains
experimentators' folders. Every experimentator's folder contains data representing one sign language.

The output of the application is a set of folders. Every folder represents experimentator.
Every folder contains 22 files. Every file represents correlation matrix for every channel.

## Getting Started

1) Clone the repo

### Prerequisites

1) Java8
2) Maven

### Installing

2) open terminal
3) cd to the project:
cd ~/auslanCoeficient
4) mvn install
5) cd ~/target
6) java -jar auslanCoeficient-jar-with-dependencies.jar [INPUTDIR] [OUTPUTDIR]
## Running the tests

mvn test

### Break down into end to end tests

There are only unit tests, testing application's methods

## Authors

* **Miroslaw Bartold** - *Initial work*
## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
