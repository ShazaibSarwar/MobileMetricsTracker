package com.shazaibsarwar.spector;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    MyAdapter adapter;
    ArrayList<Model> filterList;

    public CustomFilter ( ArrayList<Model> filterList, MyAdapter adapter )
    {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    // Filleting occurs
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        // Cheack Constraint Validity

        if (constraint != null && constraint.length()>0)
        {
            //Change to Upper Case
            constraint = constraint.toString().toUpperCase();
            //Store our filterd Models

            ArrayList<Model> filteredModles = new ArrayList<>();

            for (int i = 0; i< filterList.size(); i++)
            {
                // Check
                if (filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    // Add Model to Filterd Class
                    filteredModles.add(filterList.get(i));
                }
            }

            results.count = filteredModles.size();
            results.values = filteredModles;
        }
        else
        {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        // Notify Addapter

        adapter.notifyDataSetChanged();
    }
}
