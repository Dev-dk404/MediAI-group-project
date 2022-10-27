package data;

import android.app.Application;

import com.example.mediapp.LogInActv;

public class MyApp extends Application {
/*
the purpose of this class is to always be running on the background to avoid the need of passing data through activities
 */
    private static Data data;

    public static Data getData() {
        return data;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //createData();
    }

    public static Data createData(LogInActv logInActv){
        data = new Data(logInActv);
        //data.populateData();
        return data;
    }
}
