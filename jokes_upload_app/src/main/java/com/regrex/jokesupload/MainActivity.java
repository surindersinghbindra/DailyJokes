package com.regrex.jokesupload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.regrex.awesomejokes.R;
import com.regrex.jokesupload.ApiManager.ApiService;

import com.regrex.jokesupload.db.AppDatabase;
import com.regrex.jokesupload.model.Datum;

import com.regrex.jokesupload.model.Datum_Table;
import com.regrex.jokesupload.model.JokeModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((AppController) getApplication()).getComponent()
                .inject(this);

        apiService.getJokes().enqueue(new Callback<JokeModel>() {
            @Override
            public void onResponse(Call<JokeModel> call, Response<JokeModel> response) {
                if (response.isSuccessful()) {
                    DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                    SQLite.delete(Datum_Table.class);
                    for (final Datum datum : response.body().getData()) {
                        // run asynchronous transactions easily, with expressive builders
                        database.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                // do something in BG
                                FlowManager.getModelAdapter(Datum.class).insert(datum);
                            }
                        }).success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {

                            }
                        }).error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {
                                error.printStackTrace();
                            }
                        }).build().execute();
                    }

                }

            }

            @Override
            public void onFailure(Call<JokeModel> call, Throwable t) {

            }
        });


    }
}
