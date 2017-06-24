package com.regrex.jokesupload.excel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.regrex.awesomejokes.R;
import com.regrex.jokesupload.db.AppDatabase;
import com.regrex.jokesupload.model.CategorySingle;
import com.regrex.jokesupload.model.JokeSingle;
import com.regrex.jokesupload.model.JokeSingle_Table;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ExcelActivity extends Activity {

    TextView output;
    private DatabaseDefinition database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_excel);
        output = (TextView) findViewById(R.id.textOut);

        database = FlowManager.getDatabase(AppDatabase.class);

    }

    public void uploadCategories(View view) {
        printlnToUser("reading XLSX file from resources");
        InputStream stream = getResources().openRawResource(R.raw.excel);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            final FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            //  DatabaseReference databaseReference = firebaseDatabase.getReference().child("jokes");

            DatabaseReference databaseReference = firebaseDatabase.getReference().child("jokes_category");
            // databaseReference.removeValue();
            SQLite.delete(JokeSingle_Table.class);

            for (int r = 1; r < rowsCount; r++) {
                final Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                databaseReference.push().setValue(new CategorySingle(getCellAsInt(row, 0, formulaEvaluator), getCellAsString(row, 1, formulaEvaluator), getCellAsString(row, 2, formulaEvaluator)));

                //databaseReference.push().setValue(new JokeSingle(getCellAsInt(row, 0, formulaEvaluator), getCellAsInt(row, 1, formulaEvaluator), getCellAsString(row, 2, formulaEvaluator), getCellAsInt(row, 3, formulaEvaluator), 0, 0, ""));
                // run asynchronous transactions easily, with expressive builders
                database.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        // do something in BG
                        FlowManager.getModelAdapter(JokeSingle.class).insert(new JokeSingle(getCellAsInt(row, 0, formulaEvaluator), getCellAsInt(row, 1, formulaEvaluator), getCellAsString(row, 2, formulaEvaluator), getCellAsInt(row, 3, formulaEvaluator), 0, 0, ""));
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


               /* for (int c = 0; c < cellsCount; c++) {
                    String value = getCellAsString(row, c, formulaEvaluator);
                    String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                    printlnToUser(cellInfo);
                }*/
            }
        } catch (Exception e) {
            /* proper exception handling to be here */
            printlnToUser(e.toString());
        }
    }

    public void uploadJokes(View view) {
        printlnToUser("reading XLSX file from resources");
        InputStream stream = getResources().openRawResource(R.raw.joke_sukh);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);

            XSSFSheet sheet = workbook.getSheetAt(1);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            final FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("jokes");

            // DatabaseReference databaseReference = firebaseDatabase.getReference().child("jokes_category");
            // databaseReference.removeValue();
            SQLite.delete(JokeSingle_Table.class);

            for (int r = 1; r < rowsCount; r++) {
                final Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                // databaseReference.push().setValue(new CategorySingle(getCellAsInt(row, 0, formulaEvaluator), getCellAsString(row, 1, formulaEvaluator), getCellAsString(row, 2, formulaEvaluator)));

                databaseReference.push().setValue(new JokeSingle(getCellAsInt(row, 0, formulaEvaluator), getCellAsInt(row, 1, formulaEvaluator), getCellAsString(row, 2, formulaEvaluator), getCellAsInt(row, 3, formulaEvaluator), 0, 0, ""));
                // run asynchronous transactions easily, with expressive builders
                database.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        // do something in BG
                        FlowManager.getModelAdapter(JokeSingle.class).insert(new JokeSingle(getCellAsInt(row, 0, formulaEvaluator), getCellAsInt(row, 1, formulaEvaluator), getCellAsString(row, 2, formulaEvaluator), getCellAsInt(row, 3, formulaEvaluator), 0, 0, ""));
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


               /* for (int c = 0; c < cellsCount; c++) {
                    String value = getCellAsString(row, c, formulaEvaluator);
                    String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                    printlnToUser(cellInfo);
                }*/
            }
        } catch (Exception e) {
            /* proper exception handling to be here */
            printlnToUser(e.toString());
        }
    }

    public void onWriteClick(View view) {
        printlnToUser("writing xlsx file");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("mysheet"));
        for (int i = 0; i < 10; i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(i);
        }
        String outFileName = "filetoshare.xlsx";
        try {
            printlnToUser("writing file " + outFileName);
            File cacheDir = getCacheDir();
            File outFile = new File(cacheDir, outFileName);
            OutputStream outputStream = new FileOutputStream(outFile.getAbsolutePath());
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            printlnToUser("sharing file...");
            share(outFileName, getApplicationContext());
        } catch (Exception e) {
            /* proper exception handling to be here */
            printlnToUser(e.toString());
        }
    }

    protected String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = "" + cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                  /*  double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("dd/MM/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }*/

                    value = ((int) cellValue.getNumberValue()) + "";
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = "" + cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {
            /* proper error handling should be here */
            printlnToUser(e.toString());
        }
        return value;
    }

    protected int getCellAsInt(Row row, int c, FormulaEvaluator formulaEvaluator) {
        int value = 0;
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    //value = "" + cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                  /*  double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("dd/MM/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }*/

                    value = ((int) cellValue.getNumberValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    // value = "" + cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {
            /* proper error handling should be here */
            printlnToUser(e.toString());
        }
        return value;
    }


    /**
     * print line to the output TextView
     *
     * @param str
     */
    private void printlnToUser(String str) {
        final String string = str;
        if (output.length() > 8000) {
            CharSequence fullOutput = output.getText();
            fullOutput = fullOutput.subSequence(5000, fullOutput.length());
            output.setText(fullOutput);
           // output.setSelection(fullOutput.length());
        }
        output.append(string + "\n");
    }

    public void share(String fileName, Context context) {
        Uri fileUri = Uri.parse("content://" + getPackageName() + "/" + fileName);
        printlnToUser("sending " + fileUri.toString() + " ...");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("application/octet-stream");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
    }

}
