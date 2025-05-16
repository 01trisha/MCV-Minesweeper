package minesweeper.observ;

import minesweeper.model.Field;

public class ContextField {
    public static String[][] rebase(Field field){
        String[][] newField = new String[field.getHeight()][field.getWidth()];

        for(int i = 0; i < field.getHeight(); i++){
            for(int j = 0; j < field.getWidth(); j++){
                if (field.isCellOpen(i, j)){
                    newField[i][j] = Character.toString(field.getCell(i, j).getSym());
                }else if(field.isCellFlag(i, j)){
                    newField[i][j] = "F";
                }else{
                    newField[i][j] = "*";
                }
            }
        }
        return newField;
    }
}
