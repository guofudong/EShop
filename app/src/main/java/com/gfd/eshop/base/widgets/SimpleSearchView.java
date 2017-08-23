package com.gfd.eshop.base.widgets;


import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gfd.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleSearchView extends LinearLayout implements
        TextView.OnEditorActionListener, TextWatcher {

    @BindView(R.id.edit_query) EditText etQuery;
    @BindView(R.id.button_clear) ImageButton btnClear;

    private OnSearchListener mOnSearchListener;

    public SimpleSearchView(Context context) {
        super(context);
        init(context);
    }

    public SimpleSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @OnClick({R.id.button_search, R.id.button_clear})
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.button_search) {
            search();
        } else if (id == R.id.button_clear) {
            etQuery.setText(null);
        }
    }

    @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search();
            return true;
        }

        return false;
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        mOnSearchListener = onSearchListener;
    }

    public void search() {
        String query = etQuery.getText().toString();
        if (mOnSearchListener != null) {
            mOnSearchListener.search(query);
        }

        closeKeyBoard();
    }


    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override public void afterTextChanged(Editable s) {
        String query = etQuery.getText().toString();
        int visibility = TextUtils.isEmpty(query) ? View.INVISIBLE : View.VISIBLE;
        btnClear.setVisibility(visibility);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_search_view, this);
        ButterKnife.bind(this);

        etQuery.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etQuery.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        etQuery.setOnEditorActionListener(this);
        etQuery.addTextChangedListener(this);

    }

    // 关闭键盘
    private void closeKeyBoard() {
        etQuery.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etQuery.getWindowToken(), 0);
    }

    public interface OnSearchListener {
        void search(String query);
    }

}
