package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OperationListAdapter extends RecyclerView.Adapter<OperationListAdapter.OperationViewHolder> {

    private final LayoutInflater mInflater;
    private List<Operation> mOperations;
    private OnItemClickListner listener;

    OperationListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public OperationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new OperationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OperationViewHolder holder, final int position) {
        if (mOperations != null) {
            Operation current = mOperations.get(position);
            holder.OperationItemView.setText(current.getName() + "\n-" + current.getStrPrice() + "zł\n" +
                    current.getDate()+"\n"+current.getCategory());
        }


    }

    public void setOperations(List<Operation> Operations) {
        mOperations = Operations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mOperations != null)
            return mOperations.size();
        else return 0;
    }


    class OperationViewHolder extends RecyclerView.ViewHolder {
        private final TextView OperationItemView;


        private OperationViewHolder(View itemView) {
            super(itemView);
            OperationItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mOperations.get(position));
                    }

                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(Operation operation);
    }

    public void setOnItemClikListner(OnItemClickListner listener) {
        this.listener = listener;
    }

    public Operation getOperationAtPosition(int position) {
        return mOperations.get(position);
    }

}