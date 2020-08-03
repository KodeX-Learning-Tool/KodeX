package edu.kit.scc.git.kodex.standardplugins.rle.model.steps;

import java.util.ArrayList;
import java.util.List;

import edu.kit.scc.git.kodex.model.Tuple;
import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.pluginutils.model.content.LetterString;
import edu.kit.scc.git.kodex.pluginutils.model.content.TupleString;

public class RLEStep implements ChainStep {

  @Override
  public void decode(Content<?> input, Content<?> output) {

    TupleString tupleString = (TupleString) input;
    LetterString letterString = (LetterString) output;

    Tuple<String, Integer>[] tuples = tupleString.getTuples();
    StringBuilder letters = new StringBuilder();

    for (Tuple<String, Integer> tuple : tuples) {
      letters.append(tuple.getFirst().repeat(tuple.getSecond()));
    }

    letterString.setLetterString(letters.toString());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> input, Content<?> output) {

    LetterString letterString = (LetterString) input;
    TupleString tupleString = (TupleString) output;

    List<Tuple<String, Integer>> tuples = new ArrayList<>();

    char[] letterStringChars = letterString.getLetterString().toCharArray();

    char currentChar = letterStringChars[0];
    char newChar;
    int count = 1;

    for (int i = 1; i < letterStringChars.length; i++) {

      newChar = letterStringChars[1];

      if (newChar != currentChar) {

        tuples.add(new Tuple<>(Character.toString(currentChar), count));
        currentChar = newChar;
        count = 1;

      } else {
        count++;
      }
    }
    tupleString.setTuples(tuples.toArray((Tuple<String, Integer>[]) new Tuple[tuples.size()]));
  }
}
