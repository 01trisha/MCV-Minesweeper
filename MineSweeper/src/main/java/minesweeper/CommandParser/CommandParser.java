package minesweeper.CommandParser;

public class CommandParser {
    public String[] parse(String line){
        String[] args = line.split(" ");
        return args;
    }
}
