package com.josue.android.subnetsupernetcalc.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.josue.android.subnetsupernetcalc.R;
import com.josue.android.subnetsupernetcalc.models.Network;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josue on 04/07/2016.
 */
public class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ViewHolder> {
    private List<String> dataset;
    private ListCalcListener listCalcListener;

    public ListCalcAdapter(ListCalcListener listCalcListener) {
        this.dataset = new ArrayList<String>();
        this.listCalcListener = listCalcListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String element = dataset.get(position);
        switch (position) {
            case 0:
                holder.item_title.setText("Network   :");
                break;
            case 1:
                holder.item_title.setText("Addrress  :");
                break;
            case 2:
                holder.item_title.setText("Netmask  :");
                break;
            case 3:
                holder.item_title.setText("Broadcast:");
                break;
            case 4:
                holder.item_title.setText("First Last \n host :");
                break;
            case 5:
                holder.item_title.setText("Max host:");
                break;
            default:
                holder.item_title.setText("Size Net :");
                break;
        }
        holder.item_date.setText(element);
        holder.setOnItemClickListener(element, listCalcListener, position);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addElement(String element) {
        dataset.add(dataset.size(), element);
        notifyDataSetChanged();
    }

    public void DeleteElement() {
        dataset.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item)
        LinearLayout item;
        @Bind(R.id.item_title)
        TextView item_title;
        @Bind(R.id.item_date)
        TextView item_date;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final String element,
                                           final ListCalcListener listCalcListener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listCalcListener.onItemCLick(element, position);
                }
            });
        }
    }
}
