package com.example.rustlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rustlabs.model.Tip;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TipDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TipDialogFragment extends DialogFragment implements View.OnClickListener
{
    public static final String TAG = "TipDialog";

    private EditText mTipText;

    interface TipListener
    {
        void onTip(Tip tip);
    }

    private TipListener mTipListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dialog_tip, container, false);
        mTipText = v.findViewById(R.id.item_form_text);

        v.findViewById(R.id.item_form_button).setOnClickListener(this);
        v.findViewById(R.id.item_form_cancel).setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

        if (context instanceof TipListener)
        {
            mTipListener = (TipListener) context;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                                          ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.item_form_button:
                onSubmitClicked(v);
                break;
            case R.id.item_form_cancel:
                onCancelClicked(v);
                break;
        }
    }

    public void onSubmitClicked(View view)
    {
        Tip tip = new Tip(FirebaseAuth.getInstance().getCurrentUser(),
                          mTipText.getText().toString());

        if (mTipListener != null)
        {
            mTipListener.onTip(tip);
        }

        dismiss();
    }

    public void onCancelClicked(View view) { dismiss();}
}