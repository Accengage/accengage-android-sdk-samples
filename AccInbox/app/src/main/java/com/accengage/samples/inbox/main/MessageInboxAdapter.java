package com.accengage.samples.inbox.main;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accengage.samples.inbox.R;
import com.ad4screen.sdk.Message;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageInboxAdapter extends RecyclerView.Adapter<MessageInboxAdapter.MessageInboxHolder> {

    private static final String TAG = MessageInboxAdapter.class.getName();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM//yyyy hh:mm:ss", Locale.FRANCE);
    private final Context context;

    private ShowMessageListener listener;
    private List<Message> messages;

    MessageInboxAdapter(Context context, ShowMessageListener listener, List<Message> messages) {
        this.context = context;
        this.listener = listener;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageInboxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MessageInboxHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageInboxHolder holder, int position) {
        holder.bindMessage(messages.get(position), position);
    }

    @Override
    public int getItemCount() {
        return messages != null ? messages.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_message_inbox;
    }

    class MessageInboxHolder extends RecyclerView.ViewHolder {

        private Message message;
        private int position;

        @BindView(R.id.v_color) View view;
        @BindView(R.id.img) ImageView img;
        @BindView(R.id.tv_title) TextView title;
        @BindView(R.id.tv_date) TextView date;
        @BindView(R.id.tv_summary) TextView summary;

        @OnClick(R.id.background)
        void onMessageClick() {

            if (message == null) {
                Log.e(TAG, "message is null");
                return;
            }

            if (message.isRead()) {
                message.hasBeenDisplayedToUser(context);
            } else {
                message.setRead(true);
                message.hasBeenOpenedByUser(context);
            }

            listener.gotToMessageActivity(message, position);
        }

        MessageInboxHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindMessage(Message message, int position) {
            if (message == null) {
                return;
            }

            this.message = message;
            this.position = position;

            message.getIcon(new Message.onIconDownloadedListener() {
                @Override
                public void onIconDownloaded(Bitmap bitmap) {
                    img.setImageBitmap(bitmap);
                }
            });

            title.setText(message.getTitle());
            date.setText(dateFormat.format(message.getSendDate()));
            summary.setText(message.getText());
            view.setBackgroundColor(message.isArchived() ?
                    context.getResources().getColor(R.color.red)
                    : message.isRead()
                    ? context.getResources().getColor(R.color.white)
                    : context.getResources().getColor(R.color.blue_light));
        }
    }
}
