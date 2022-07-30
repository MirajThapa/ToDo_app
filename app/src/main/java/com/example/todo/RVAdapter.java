package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.databinding.SingleCardBinding;

import javax.security.auth.callback.Callback;

public class RVAdapter extends ListAdapter<Note,RVAdapter.ViewHolder> {

    public RVAdapter(){
        super(CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDesc().equals(newItem.getDesc());
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = getItem(position);
        holder.binding.textView.setText(note.getTitle());
        holder.binding.textView2.setText(note.getDesc());
    }

    public Note getNote(int position)
    {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        SingleCardBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleCardBinding.bind(itemView);
        }
    }

}
