package com.example.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        News currentNews = getItem(position);
        View listItemView = convertView;
        if (listItemView == null) {
            viewHolder = new ViewHolder();
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_main, parent, false);

            viewHolder.Title = listItemView.findViewById(R.id.Title);
            viewHolder.Section = listItemView.findViewById(R.id.Section);
            viewHolder.pillarname = listItemView.findViewById(R.id.pillarname);
            viewHolder.Date = listItemView.findViewById(R.id.date);
            viewHolder.Author = listItemView.findViewById(R.id.Author);

            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        viewHolder.Title.setText(currentNews.getmTitle());
        viewHolder.Section.setText(currentNews.getmSection());
        viewHolder.Date.setText(currentNews.getmDate());
        viewHolder.pillarname.setText(currentNews.getmPillarName());
        viewHolder.Author.setText(currentNews.getmAuthor());
        return listItemView;
    }

    private static class ViewHolder {
        TextView Title;
        TextView Section;
        TextView Date;
        TextView pillarname;
        TextView Author;
    }
}
