package com.coder.seadoc.views;


import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.coder.seadoc.R;

/**
 * Created by XL on 2017/7/4.
 */

public class MyActionProvider extends ActionProvider {
    public MyActionProvider(Context context)
    {
        super(context);
    }

    @Override
    public View onCreateActionView()
    {
        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu)
    {
        subMenu.clear();

        subMenu.add("显示英文")
                .setIcon(R.mipmap.ic_launcher)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {

                        return true;
                    }
                });
        subMenu.add("显示中文")
                .setIcon(R.mipmap.ic_launcher)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        return true;
                    }
                });
        subMenu.add("中英混排")
                .setIcon(R.mipmap.ic_launcher)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        return true;
                    }
                });
    }

    @Override
    public boolean hasSubMenu()
    {
        return true;
    }
}
