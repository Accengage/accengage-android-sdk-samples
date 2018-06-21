package com.accengage.samples.inbox.messages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.accengage.samples.inbox.R;
import com.ad4screen.sdk.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButtonMessageAdapter extends RecyclerView.Adapter<ButtonMessageAdapter.ButtonMessageHolder> {

    private final static String TAG = ButtonMessageAdapter.class.getName();

    private Context context;
    private List<Message.Button> buttons;

    ButtonMessageAdapter(Context context, List<Message.Button> buttons) {
        this.context = context;
        this.buttons = buttons;
    }

    @NonNull
    @Override
    public ButtonMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ButtonMessageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonMessageHolder holder, int position) {
        holder.bindButton(buttons.get(position));
    }

    @Override
    public int getItemCount() {
        return buttons != null ? buttons.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_button_message;
    }

    class ButtonMessageHolder extends RecyclerView.ViewHolder {

        private Message.Button button;

        @BindView(R.id.btn_message) Button btn_message;

        @OnClick(R.id.btn_message)
        void OnButtonClick() {
            if (button == null) {
                Log.e(TAG, "Button is null");
                return;
            }

            button.hasBeenClickedByUser(context);
            button.click(context);
        }

        ButtonMessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindButton(Message.Button button) {
            if (button == null) {
                return;
            }

            this.button = button;
            btn_message.setText(button.getTitle());
        }
    }
}
