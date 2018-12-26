package a3embed.example.com.rxjavaretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView apiResponse = findViewById(R.id.apiResponse);

        Button getData = findViewById(R.id.getData);

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetroEndPoints mAPIService = ApiUtils.getAPIService();

                Observable<MessageDataModelRetro> btcObservable = mAPIService.savePost(2, "ABC", "M");

                Observer<MessageDataModelRetro> observer = new Observer<MessageDataModelRetro>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MessageDataModelRetro messageDataModelRetro) {
                        String message = messageDataModelRetro.getMessage();
                        DataModelRetro dataModelRetro = messageDataModelRetro.getData();
                        String token = dataModelRetro.getToken();
                        String sid = dataModelRetro.getSid();
                        String type = dataModelRetro.getType();
                        String responseValue = "Status: " + message + "\nData:-\nToken Values: " + token + "\n\nSID value: " + sid + "\n\nType Value: " + type;
                        apiResponse.setText(responseValue);
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponse.setText(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "Data Got", Toast.LENGTH_SHORT).show();
                    }
                };

                btcObservable.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            }
        });
    }
}
