package com.pwr.mycookbook.data.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.service_db.RecipeIngredientRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by olaku on 13.12.2017.
 */

public class RecipePDF {

    private static final String TITLE_FONT = "res/font/amatic_bold.ttf";
    private static final String SUBTITLE_FONT = "res/font/caviar_dreams_bold.ttf";
    private static final String TEXT_FONT = "res/font/caviardreams.ttf";
    private static final String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
    private static final int titleSize = 40;
    private static final int subtitleSize = 30;
    private static final int textSize = 28;
    private File file;
    private RecipeIngredientRepository recipeIngredientRepository;



    public File writeRecipeToPDF(Recipe recipe, Context context){
        Document doc = new Document();
        recipeIngredientRepository = new RecipeIngredientRepository(context);
        try {
            BaseFont title_baseFont = BaseFont.createFont(TITLE_FONT, BaseFont.CP1250, BaseFont.EMBEDDED);
            BaseFont subtitle_baseFont = BaseFont.createFont(SUBTITLE_FONT, BaseFont.CP1250, BaseFont.EMBEDDED);
            BaseFont text_baseFont = BaseFont.createFont(TEXT_FONT, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font titleFont = new Font(title_baseFont, titleSize, Font.NORMAL, BaseColor.BLACK);
            Font subtitleFont = new Font(subtitle_baseFont, subtitleSize, Font.NORMAL, BaseColor.BLACK);
            Font textFont = new Font(text_baseFont, textSize, Font.NORMAL, BaseColor.BLACK);


            File dir = new File(PATH);
            if(!dir.exists())
                dir.mkdirs();

            file = new File(dir, "Recipe_" + System.currentTimeMillis() + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            doc.open();

            Bitmap bitmap = null;
            if(recipe.getPhoto_bitmap()!=null){
                bitmap = recipe.getPhoto_bitmap();

            }else if(!recipe.getPhoto().isEmpty()){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeFile(recipe.getPhoto(), options);
            }
            if(bitmap !=null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
                Image image = Image.getInstance(stream.toByteArray());
                image.setAlignment(Image.MIDDLE);
                doc.add(image);
            }

            Paragraph title_paragraph = new Paragraph(recipe.getTitle(), titleFont);

            doc.add(title_paragraph);

            doc.add(new Paragraph(" "));

            String string_time = context.getString(R.string.doc_time) + " " + String.valueOf(recipe.getTime()) + " " + "min.";
            Paragraph time_paragraph = new Paragraph(string_time, subtitleFont);
            time_paragraph.setAlignment(Paragraph.ALIGN_LEFT);

            doc.add(time_paragraph);

            String string_portion = context.getString(R.string.doc_portion) + " " + recipe.getPortion();
            Paragraph portion_paragraph = new Paragraph(string_portion, subtitleFont);
            portion_paragraph.setAlignment(Paragraph.ALIGN_LEFT);

            doc.add(portion_paragraph);

            doc.newPage();

            String string_ingredients = context.getString(R.string.doc_ingredients) ;
            Paragraph ingredients_paragraph = new Paragraph(string_ingredients, subtitleFont);
            ingredients_paragraph.setAlignment(Paragraph.ALIGN_LEFT);

            doc.add(ingredients_paragraph);

            List<Recipe_Ingredient> recipe_ingredientList = recipeIngredientRepository.getIngredientsForRecipe(recipe.getId());

            for(Recipe_Ingredient recipe_ingredient: recipe_ingredientList){
                String string_ingredient = recipe_ingredient.getName();
                Paragraph ingredient_paragraph = new Paragraph(string_ingredient, textFont);
                ingredient_paragraph.setAlignment(Paragraph.ALIGN_LEFT);

                doc.add(ingredient_paragraph);
            }

            doc.newPage();

            String string_description = context.getString(R.string.doc_description);
            Paragraph description_paragraph = new Paragraph(string_description, subtitleFont);
            description_paragraph.setAlignment(Paragraph.ALIGN_LEFT);

            doc.add(description_paragraph);

            String string_description_text = recipe.getDescription();
            Paragraph description_text_paragraph = new Paragraph(string_description_text, textFont);
            description_text_paragraph.setAlignment(Paragraph.ALIGN_LEFT);

            doc.add(description_text_paragraph);

            String string_note = context.getString(R.string.doc_note);
            Paragraph note_paragraph = new Paragraph(string_note, subtitleFont);
            note_paragraph.setAlignment(Paragraph.ALIGN_LEFT);

            doc.add(note_paragraph);

            String string_note_text = recipe.getNote();
            Paragraph note_text_paragraph = new Paragraph(string_note_text, textFont);
            note_text_paragraph.setAlignment(Paragraph.ALIGN_LEFT);

            doc.add(note_text_paragraph);


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
        }

        return file;

    }

}
