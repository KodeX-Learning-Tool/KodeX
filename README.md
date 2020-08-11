# KodeX

> educational tool for encoding theorie at schools


[![pipeline status](https://git.scc.kit.edu/pse-kodierungstheorie-tool/kodex/badges/master/pipeline.svg)](https://git.scc.kit.edu/pse-kodierungstheorie-tool/kodex/-/commits/master) [![coverage report](https://git.scc.kit.edu/pse-kodierungstheorie-tool/kodex/badges/master/coverage.svg)](https://git.scc.kit.edu/pse-kodierungstheorie-tool/kodex/-/commits/master)

KodeX is a result of a univeristy project, where a team of students has to build an application for a given task.
In our case, we created a tool that is supposed to help students and teachers to develop a deeper understanding about the theory behind data encoding.
This is a crutial part of the computer sience curriculum for grade 7 and beyond. Students should learn, that all kinds of produced and received data has to be encoded
to be processed by machines.
This is where this tool comes into play. It is supposed to help with the visualization of different encoding and decoding procedures to make the concepts behind them easier to grasp.
Furthermore, KodeX allowes to play with diffrent stages inside such a process, by editing the processed data and observing the resulting changes.

KodeX is part of the "Open-Source-Labor" at Karlsruhe Institut für Technologie (KIT).


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

To be able to run KodeX on your local machine you need the following:

- Java 11 or newer
- Maven (3.6 or newer recommended, older version have not been tested)

To check your java version run:
```
java --version
```

If you want to develop for this project, make sure that your IDE also uses the correct JDK.

### Run the project

There are three ways you can use this tool.

1. Clone the repository and open it with an IDE of your choice, that supports maven. ([See Installing](#installing)) Run the projects main.
2. Use the latest build version, found [here](https://git.scc.kit.edu/pse-kodierungstheorie-tool/kodex/-/releases). Download the jar and run it with ``` java -jar PATH/KodeX.jar ```, where PATH is the path to the jar and KodeX is the name of the latest jar.
3. Clone the repository and run ``` mvn clean install ``` to create a snapshot of the latest version inside the target direcotry. To run the jar follow step two.

### Installing

To install this repo locally and start developing you will first need to clone this repository via Git.

Run the following code inside the desired directory.

```
git clone https://git.scc.kit.edu/pse-kodierungstheorie-tool/kodex.git
```

Afterwards make sure the repo builds and all dependencies are downloaded by running:

```
mvn clean install
```

If you are running this inside an IDE, you might have to refresh your project or update it through maven, to make the changes visible and accessible.