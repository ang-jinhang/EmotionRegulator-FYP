package ang.jinhang.emotionregulator.emotionregulation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ang.jinhang.emotionregulator.R

class NoRegulation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noregulation)

        val leaveButton = findViewById<Button>(R.id.noRegulation_leaveButton)
        leaveButton.setOnClickListener(View.OnClickListener {
            this.finishAffinity()
        })
    }
}