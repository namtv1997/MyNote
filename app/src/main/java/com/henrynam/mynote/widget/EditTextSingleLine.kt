package com.henrynam.mynote.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.henrynam.mynote.R
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.edit_text_single_line.view.*
import java.util.concurrent.TimeUnit

class EditTextSingleLine @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * Callback for `keyword` input
     */
    var inputListener: InputListener? = null

    private var dispose: Disposable

    private var text: String = ""
        set(value) {
            field = value
            setEditText(value)
        }

    private var hintText: String = ""
        set(value) {
            field = value
            setHint(value)
        }

    private var inputType: Int = 0x00000001
        set(value) {
            field = value
            setInputTypeText(value)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.edit_text_single_line, this, true)

        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextSingleLine, 0, 0)
        try {
            text = array.getString(R.styleable.EditTextSingleLine_text) ?: ""
            hintText = array.getString(R.styleable.EditTextSingleLine_hint) ?: ""
            inputType = array.getInt(R.styleable.EditTextSingleLine_inputType, 0x00000001)
        } finally {
            array.recycle()
        }

        dispose = RxTextView.textChangeEvents(edtInput)
            .skipInitialValue()
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<TextViewTextChangeEvent>() {
                override fun onComplete() {
                }

                override fun onError(e: Throwable) {
                }

                override fun onNext(t: TextViewTextChangeEvent) {
                    val s = t.text()
                    ivClear.visibility = if (s.isNotEmpty()) View.VISIBLE else View.INVISIBLE
                    inputListener?.onInput(s.trim())
                }
            })

        ivClear.setOnClickListener { edtInput.setText("") }
    }

    // Set text for EditText
    fun setEditText(text: String) {
        edtInput.setText(text)
    }

    // Set hint for EditText
    private fun setHint(hint: String) {
        edtInput.hint = hint
    }

    private fun setInputTypeText(inputType: Int) {
        edtInput.inputType = inputType
    }

    // Get text has input to EditText
    fun getText(): String {
        return edtInput.text.toString().trim()
    }

    fun onDestroy() {
        if (!dispose.isDisposed) {
            dispose.dispose()
        }
    }

    /**
     * Interface definition for a callback to be invoked when user enter search `keyword`
     */
    interface InputListener {
        /**
         * Fired when user enter search input `keyword`
         *
         * @param input Keyword entered by user
         */
        fun onInput(input: CharSequence)
    }
}