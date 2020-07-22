package kodex.standardplugins.rle.model.steps;

import java.util.ArrayList;
import java.util.List;

import kodex.model.Tuple;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.LetterString;
import kodex.pluginutils.model.content.TupleString;

public class RLEStep extends ChainStep {

    @Override
    public void encode(Content left, Content right) {
        
        LetterString letterString = (LetterString) left;
        TupleString tupleString = (TupleString) right;
        
        List<Tuple<String, Integer>> tuples = new ArrayList<>();
        
        char[] letterStringChars = letterString.getLetterString().toCharArray();
        
        char currentChar = letterStringChars[0];
        char newChar;
        int count = 1;
        
        for (int i = 1; i < letterStringChars.length; i++) {
            
            newChar = letterStringChars[i];

            if (newChar != currentChar) {
                
                tuples.add(new Tuple<>(Character.toString(currentChar), count));

                currentChar = newChar;
                count = 1;
                
            } else {
                count++;
            }
        }
        
        //add last read char with its count
        tuples.add(new Tuple<>(Character.toString(currentChar), count));

        tupleString.setTuples(tuples.toArray((Tuple<String, Integer>[]) new Tuple[tuples.size()]));
    }

    @Override
    public void decode(Content right, Content left) {
        
        TupleString tupleString = (TupleString) left;
        LetterString letterString = (LetterString) right;
        
        Tuple<String, Integer>[] tuples = tupleString.getTuples();
        StringBuilder letters = new StringBuilder();
        
        for (Tuple<String, Integer> tuple : tuples) {
            letters.append(tuple.getFirst().repeat(tuple.getSecond()));
        }
        
        letterString.setLetterString(letters.toString());
    }

}
