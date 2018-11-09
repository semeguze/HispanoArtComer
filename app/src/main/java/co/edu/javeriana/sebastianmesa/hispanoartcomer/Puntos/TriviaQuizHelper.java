package co.edu.javeriana.sebastianmesa.hispanoartcomer.Puntos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class TriviaQuizHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "TQuiz.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 3;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    TriviaQuizHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        ArrayList<TriviaQuestion> arraylist = new ArrayList<>();

        arraylist.add(new TriviaQuestion("¿Cómo consigue fibra nuestro cuerpo?", "Comidas no procesadas", "Comidas altamente procesadas", "Leche", "Carne", "Comidas no procesadas"));
        arraylist.add(new TriviaQuestion("¿Qué ayuda a tu cuerpo a procesar la vitamina D?", "Dormir bien", "La exposición a la luz solar", "Hacer ejercicio", "Tomar mucha agua", "La exposición a la luz solar"));
        arraylist.add(new TriviaQuestion("Vas al gimnasio, ejercitas con intensidad y, cuando te pesas, descubres que has bajado 1 kilo. ¿Qué sucedió?", "Perdiste un kilo de grasa", "Perdiste un kilo de agua", "Ambas", "Ninguna de las anteriores", "Perdiste un kilo de agua"));
        arraylist.add(new TriviaQuestion("El siguiente grupo alimenticio es la principal fuente de energía para el cuerpo", "Frutas y vegetales", "Proteína", "Carbohidratos", "Ninguna de las anteriores", "Carbohidratos"));
        arraylist.add(new TriviaQuestion("¿Cuál de estos grupos alimenticios ayuda a construir músculos en el cuerpo?", "Proteína", "Vitaminas", "Carbohidratos", "Grasa", "Proteína"));
        arraylist.add(new TriviaQuestion("¿Cada cuánto es recomendable comer?", "5 veces al día", "2 veces al día", "3 veces al día", "7 veces al día", "5 veces al día"));
        arraylist.add(new TriviaQuestion("¿Cuál de estos grupos de alimentos ayudan a prevenir el cáncer?", "Carnes rojas y frituras", "Quesos amarillos y frutos secos", "El ajo, las zanahorias y la rúcula", "Las papas y los cereales", "El ajo, las zanahorias y la rúcula"));
        arraylist.add(new TriviaQuestion("¿Cuál de estos alimentos ayuda a tu flora intestinal?", "Chocolate", "Leche cultivada", "Café", "Verduras verdes", "Leche cultivada"));
        arraylist.add(new TriviaQuestion("¿Cuántas porciones de fruta hay que consumir al día?", "Entre 1 y 2", "Entre 2 y 3", "Entre 2 y 4", "Entre 3 y 6", "Entre 2 y 4"));
        arraylist.add(new TriviaQuestion("¿Para qué sirve la pirámide alimenticia?", "Para pintarla", "Para ordenar los tipos de alimentos según su color", "Para ordenar los tipos de alimentos según su importancia", "Para ordenar los tipos de alimentos según su procedencia", "Para ordenar los tipos de alimentos según su importancia"));
        arraylist.add(new TriviaQuestion("¿Cuándo es más recomendable comer fruta?", "A cualquier hora", "Nada más levantarse", "Siempre, excepto después de cada comida porque engorda más", "Al acostarse", "Siempre, excepto después de cada comida porque engorda más"));
        arraylist.add(new TriviaQuestion("¿Cuál es la dieta más sana?", "Dieta basada en hidratos de carbono", "Dieta cantábrica", "Dieta basada en proteínas", "Dieta mediterránea", "Dieta mediterránea"));
        arraylist.add(new TriviaQuestion("¿Cuál de estas grasas es más recomendable para cocinar?", "Aceite de girasol", "Mantequilla", "Todas son iguales", "Aceite de oliva", "Aceite de oliva"));
        arraylist.add(new TriviaQuestion("¿Por qué son importantes las verduras?", "Proporcionan vitaminas", "Porque son verdes", "Porque son naturales", "Proporcionan proteínas", "Proporcionan vitaminas"));
        arraylist.add(new TriviaQuestion("¿Dónde hay más grasa?", "En el hígado", "En la leche", "En los huevos", "En el pollo", "En el hígado"));
        arraylist.add(new TriviaQuestion("Después de hacer ejercicio, ¿Qué es más recomendable comer?", "Carne", "Pescado", "Dulces", "Frutos secos", "Frutos secos"));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));
//        arraylist.add(new TriviaQuestion("", "", "", "", "", ""));



//        arraylist.add(new TriviaQuestion("Who is the father of Geometry ?", "Aristotle", "Euclid", "Pythagoras", "Kepler", "Euclid"));
//
//        arraylist.add(new TriviaQuestion("Who was known as Iron man of India ?", "Govind Ballabh Pant", "Jawaharlal Nehru", "Subhash Chandra Bose", "Sardar Vallabhbhai Patel", "Sardar Vallabhbhai Patel"));
//
//        arraylist.add(new TriviaQuestion("The first woman in space was ?", "Valentina Tereshkova", "Sally Ride", "Naidia Comenci", "Tamara Press", "Valentina Tereshkova"));
//
//        arraylist.add(new TriviaQuestion("Who is the Flying Sikh of India ?", "Mohinder Singh", "Joginder Singh", "Ajit Pal Singh", "Milkha singh", "Milkha singh"));
//
//        arraylist.add(new TriviaQuestion("The Indian to beat the computers in mathematical wizardry is", "Ramanujam", "Rina Panigrahi", "Raja Ramanna", "Shakunthala Devi", "Shakunthala Devi"));
//
//        arraylist.add(new TriviaQuestion("Who is Larry Pressler ?", "Politician", "Painter", "Actor", "Tennis player", "Politician"));
//
//        arraylist.add(new TriviaQuestion("Michael Jackson is a distinguished person in the field of ?", "Pop Music", "Jounalism", "Sports", "Acting", "Pop Music"));
//
//        arraylist.add(new TriviaQuestion("The first Indian to swim across English channel was ?", "V. Merchant", "P. K. Banerji", "Mihir Sen", "Arati Saha", "Mihir Sen"));
//
//        arraylist.add(new TriviaQuestion("Who was the first Indian to make a movie?", "Dhundiraj Govind Phalke", " Asha Bhonsle", " Ardeshir Irani", "V. Shantaram", "Dhundiraj Govind Phalke"));
//
//        arraylist.add(new TriviaQuestion("Who is known as the ' Saint of the gutters ?", "B.R.Ambedkar", "Mother Teresa", "Mahatma Gandhi", "Baba Amte", "Mother Teresa"));
//
//        arraylist.add(new TriviaQuestion("Who invented the famous formula E=mc^2", "Albert Einstein", "Galilio", "Sarvesh", "Bill Gates", "Albert Einstein"));
//
//        arraylist.add(new TriviaQuestion("Who is elected as president of us 2016", "Donald Trump", "Hilary Clinton", "Jhon pol", "Barack Obama", "Donald Trump"));
//
//        arraylist.add(new TriviaQuestion("Who was the founder of company Microsoft", "Bill Gates", "Bill Clinton", "Jhon rio", "Steve jobs", "Bill Gates"));
//
//        arraylist.add(new TriviaQuestion("Who was the founder of company Apple ?", "Steve Jobs", "Steve Washinton", "Bill Gates", "Jobs Wills", "Steve Jobs"));
//
//        arraylist.add(new TriviaQuestion("Who was the founder of company Google ?", "Steve Jobs", "Bill Gates", "Larry Page", "Sundar Pichai", "Larry Page"));
//
//        arraylist.add(new TriviaQuestion("Who is know as god of cricket ?", "Sachin Tendulkar", "Kapil Dev", "Virat Koli", "Dhoni", "Sachin Tendulkar"));
//
//        arraylist.add(new TriviaQuestion("who has won ballon d'or of 2015 ?", "Lionel Messi", "Cristiano Ronaldo", "Neymar", "Kaka", "Lionel Messi"));
//
//        arraylist.add(new TriviaQuestion("who has won ballon d'or of 2014 ?", "Neymar", "Lionel Messi", "Cristiano Ronaldo", "Kaka", "Cristiano Ronaldo"));
//
//        arraylist.add(new TriviaQuestion("the Founder of the most famous gaming platform steam is ?", "Bill Cliton", "Bill Williams", "Gabe Newell", "Bill Gates", "Gabe Newell"));

        this.addAllQuestions(arraylist);

    }


    private void addAllQuestions(ArrayList<TriviaQuestion> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (TriviaQuestion question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTA, question.getOptA());
                values.put(OPTB, question.getOptB());
                values.put(OPTC, question.getOptC());
                values.put(OPTD, question.getOptD());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    List<TriviaQuestion> getAllOfTheQuestions() {

        List<TriviaQuestion> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            TriviaQuestion question = new TriviaQuestion();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setOptA(cursor.getString(2));
            question.setOptB(cursor.getString(3));
            question.setOptC(cursor.getString(4));
            question.setOptD(cursor.getString(5));
            question.setAnswer(cursor.getString(6));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();

        Collections.shuffle(questionsList);

        return questionsList;
    }
}
