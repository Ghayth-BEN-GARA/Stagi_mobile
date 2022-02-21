package com.ghayth.stagi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.ghayth.stagi.Model.PresentationItem;
import com.ghayth.stagi.R;
import java.util.List;

public class PresentationAdapter extends PagerAdapter {
    private Context context;
    private List<PresentationItem> presentationItemList;

    public PresentationAdapter(Context context, List<PresentationItem> presentationItemList) {
        this.context = context;
        this.presentationItemList = presentationItemList;
    }

    @Override
    public int getCount() {
        return presentationItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutIntro = inflater.inflate(R.layout.items_presentation,null);

        ImageView image = (ImageView) layoutIntro.findViewById(R.id.image);
        TextView titre = (TextView) layoutIntro.findViewById(R.id.titre);
        TextView description = (TextView) layoutIntro.findViewById(R.id.description);

        titre.setText(presentationItemList.get(position).getTitle());
        description.setText(presentationItemList.get(position).getDescription());
        image.setImageResource(presentationItemList.get(position).getImage());

        container.addView(layoutIntro);
        return layoutIntro;
    }
}
