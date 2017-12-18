package com.restart.autosizingtextviews;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Android doc
 * https://developer.android.com/guide/topics/ui/look-and-feel/autosizing-textview.html
 */
public class MainActivity extends AppCompatActivity implements TextWatcher {

    /**
     * Set to true if should use widgets from XML rather than programmatically
     */
    private static final boolean INITIALIZE_THROUGH_XML = false;

    /**
     * Only used for rotation purposes. After rotation we get the value of EditText and apply it to TextViews.
     */
    private static final String EDIT_TEXT_VALUE = "edit_text_value";

    /**
     * EditText to enter our text, and three TextViews to see the differences between the three different approaches to
     * AutoSizing TextViews. Default, Granularity, and Preset Sizes
     */
    private EditText editText;
    private TextView defaultTextView;
    private TextView granularityTextView;
    private TextView presetSizesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // There is two layouts, one is for api 26 and above, other for any other API versions.
        setContentView(R.layout.activity_main);
        final int COMPLEX_UNIT_SP = TypedValue.COMPLEX_UNIT_SP;
        final int[] preset_integer_sizes = new int[]{10, 12, 20, 40, 100};

        // Load the widgets from our XML only. The support library will load if phone is below API 26.
        if (INITIALIZE_THROUGH_XML) {
            editText = findViewById(R.id.edit_text);
            defaultTextView = findViewById(R.id.default_text_view);
            granularityTextView = findViewById(R.id.granularity_text_view);
            presetSizesTextView = findViewById(R.id.preset_sizes_text_view);

        /* Load the widgets manually into our layouts. This is how you would initialize Autosizing TextViews
        programmatically when not using any support library */
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initializeWidgets(savedInstanceState, false);
            replaceWidgetsInLayout();
            defaultTextView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            granularityTextView.setAutoSizeTextTypeUniformWithConfiguration(12, 100, 2, COMPLEX_UNIT_SP);
            presetSizesTextView.setAutoSizeTextTypeUniformWithPresetSizes(preset_integer_sizes, COMPLEX_UNIT_SP);

            // Just like the if statement above, instead we are using the support library.
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            initializeWidgets(savedInstanceState, true);
            replaceWidgetsInLayout();
            TextViewCompat.setAutoSizeTextTypeWithDefaults(defaultTextView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(granularityTextView, 12, 100, 2,
                    COMPLEX_UNIT_SP);
            TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(presetSizesTextView, preset_integer_sizes,
                    COMPLEX_UNIT_SP);
        }

        /* Set up a listener for any text changes, and call the method to start it off with what is already in the
        EditText */
        editText.addTextChangedListener(this);
        afterTextChanged(null);
    }

    /**
     * Initialize widgets if we decided not to create them through XML. It will be mostly the same for support and
     * non-support libraries only that one needs regular TextView, other needs AppCompatTextView. You can simply use
     * AppCompatTextView for both cases to simplify since AppCompatTextView extends TextView.
     */
    private void initializeWidgets(Bundle savedInstanceState, boolean isSupportLibrary) {
        /* It is not recommended to use the value "wrap_content" for the layout_width or layout_height attributes of
        a TextView. It may produce unexpected results. */
        final int twoHundredDensityPixel = (int) getResources().getDimension(R.dimen.height);
        final LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, twoHundredDensityPixel);

        editText = new EditText(this);
        if (savedInstanceState != null && savedInstanceState.getString(EDIT_TEXT_VALUE) != null)
            editText.setText(savedInstanceState.getString(EDIT_TEXT_VALUE));
        else
            editText.setText(getString(R.string.hello_world));

        if (isSupportLibrary) {
            defaultTextView = new AppCompatTextView(this);
            granularityTextView = new AppCompatTextView(this);
            presetSizesTextView = new AppCompatTextView(this);
        } else {
            defaultTextView = new TextView(this);
            granularityTextView = new TextView(this);
            presetSizesTextView = new TextView(this);
        }

        editText.setLayoutParams(editTextParams);
        defaultTextView.setLayoutParams(textViewParams);
        granularityTextView.setLayoutParams(textViewParams);
        presetSizesTextView.setLayoutParams(textViewParams);
    }

    /**
     * Remove all layouts from our parent container. Used when we create our widgets programmatically
     * so the widgets from XML {@code R.layout.activity_main} are removed and then later replaced.
     */
    private void replaceWidgetsInLayout() {
        LinearLayout linearLayout = findViewById(R.id.linear_layout_container);
        linearLayout.removeAllViews();
        linearLayout.addView(editText);
        linearLayout.addView(defaultTextView);
        linearLayout.addView(granularityTextView);
        linearLayout.addView(presetSizesTextView);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // do nothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // do nothing
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // Update the TextViews with what is in our EditText
        defaultTextView.setText(editText.getText().toString());
        granularityTextView.setText(editText.getText().toString());
        presetSizesTextView.setText(editText.getText().toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(EDIT_TEXT_VALUE, editText.getText().toString());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
