package eu.epitech.clement.cauproject1.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.epitech.clement.cauproject1.R;

public class DrawerExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private Map<String, String[]> listDataChild;

    public DrawerExpandableListAdapter(Context context, List<String> listDataHeader, Map<String, String[]> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataChild.get(listDataHeader.get(groupPosition))[childPosititon];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        ViewHolderItem holderItem;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_item, null);
            holderItem = new ViewHolderItem(convertView);
            convertView.setTag(holderItem);
        } else holderItem = (ViewHolderItem) convertView.getTag();
        holderItem.itemName.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        ViewHolderGroup holderGroup;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_category_item, null);
            holderGroup = new ViewHolderGroup(convertView);
            convertView.setTag(holderGroup);
        } else
            holderGroup = (ViewHolderGroup) convertView.getTag();
        if (isExpanded) {
            holderGroup.arrow.setImageResource(R.drawable.up_arrow);
        } else {
            holderGroup.arrow.setImageResource(R.drawable.angle_arrow_down);
        }
        holderGroup.itemName.setTypeface(null, Typeface.BOLD);
        holderGroup.itemName.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ViewHolderGroup {
        @BindView(R.id.lblListHeader) TextView itemName;
        @BindView(R.id.header_arrow) ImageView arrow;

        public ViewHolderGroup(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public class ViewHolderItem {
        @BindView(R.id.lblListItem) TextView itemName;

        public ViewHolderItem(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
