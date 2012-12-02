package edu.champlain.csi319.findstuff.widget;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HashMapAdapter<K, V> extends BaseAdapter
{
    private static final String TAG = HashMapAdapter.class.getSimpleName();
    private Map<K, V> objects;
    private final Object lock = new Object();
    private int resource;
    private int dropDownResource;
    private int fieldId;
    private boolean notifyOnChange;
    private Context context;
    private HashMap<K, V> originalValues;
    private LayoutInflater inflater;
    
    public HashMapAdapter(Context context, int resource) 
    {
        init(context, resource, 0, new HashMap<K, V>());
    }
    
    public HashMapAdapter(Context context, int resource, int textViewResourceId) 
    {
        init(context, resource, textViewResourceId, new HashMap<K, V>());
    }
    
    public HashMapAdapter(Context context, int resource, Map<K, V> objects) 
    {
        init(context, resource, 0, objects);
    }
    
    public HashMapAdapter(Context context, int resource, int textViewResourceId, Map<K, V> objects) 
    {
        init(context, resource, textViewResourceId, objects);
    }
    
    public void put(K key, V value)
    {
        synchronized(lock)
        {
            if(originalValues != null)
            {
                originalValues.put(key, value);
            }
            else
            {
                objects.put(key, value);
            }
        }
        
        if(notifyOnChange)
        {
            notifyDataSetChanged();
        }
    }
    
    public void putAll(Map<? extends K, ? extends V> map)
    {
        synchronized(lock)
        {
            if(originalValues != null)
            {
                originalValues.putAll(map);
            }
            else
            {
                objects.putAll(map);
            }
        }
        
        if(notifyOnChange)
        {
            notifyDataSetChanged();
        }
    }
    
    public void remove(K key)
    {
        synchronized(lock)
        {
            if(originalValues != null)
            {
                originalValues.remove(key);
            }
            else
            {
                objects.remove(key);
            }
        }
        
        if(notifyOnChange)
        {
            notifyDataSetChanged();
        }       
    }
    
    public void clear()
    {
        synchronized(lock)
        {
            if(originalValues != null)
            {
                originalValues.clear();
            }
            else
            {
                objects.clear();
            }
        }
        
        if(notifyOnChange)
        {
            notifyDataSetChanged();
        }
    }
    
    @Override
    public void notifyDataSetChanged() 
    {
        super.notifyDataSetChanged();
        notifyOnChange = true;
    }
    
    public void setNotifyOnChange(boolean notifyOnChange) 
    {
        this.notifyOnChange = notifyOnChange;
    }
    
    public void replaceAll(Map<K, V> objects)
    {
        synchronized(lock)
        {
            if(originalValues != null)
            {
                originalValues = (HashMap<K, V>)objects;
            }
            else
            {
               this.objects = objects;
            }
        }
        
        if(notifyOnChange)
        {
            notifyDataSetChanged();
        }
    }
    
    private void init(Context context, int resource, int textViewResourceId, Map<K, V> objects) 
    {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = dropDownResource = resource;
        this.objects = objects;
        this.fieldId = textViewResourceId;
    }
    
    public Context getContext()
    {
        return context;
    }
    
    @Override
    public int getCount()
    {
        return objects.size();
    }

    @Override
    public K getItem(int position)
    {
        return getKey(position);
    }
    
    public K getKey(int position)
    {
        int i = 0;
        for(Map.Entry<K, V> entry : objects.entrySet())
        {
            if(i == position)
            {
                return entry.getKey();
            }
            i++;
        }
        return null;        
    }
    
    public V getValue(K key)
    {
        return objects.get(key);
    }

    /**
     * Keys must be unique by definition of a Map.  However, many keys can have the same value.
     * This simply returns the first match, if any.
     * @param value
     * @return
     */
    public K getFirstKey(V value)
    {
        for(Map.Entry<K, V> entry : objects.entrySet())
        {
            if(entry.getValue().equals(value))
            {
                return entry.getKey();
            }
        }
        return null;
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return createViewFromResource(position, convertView, parent, resource);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource)
    {
        View view;
        TextView text;

        if(convertView == null) 
        {
            view = inflater.inflate(resource, parent, false);
        } 
        else 
        {
            view = convertView;
        }

        try 
        {
            if(fieldId == 0) 
            {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = (TextView)view;
            } 
            else 
            {
                //  Otherwise, find the TextView field within the layout
                text = (TextView)view.findViewById(fieldId);
            }
        } 
        catch(ClassCastException e) 
        {
            Log.e(TAG, "You must supply a resource ID for a TextView");
            throw new IllegalStateException(TAG + " requires the resource ID to be a TextView", e);
        }

        K item = getItem(position);
        if(item instanceof CharSequence) 
        {
            text.setText((CharSequence)item);
        } 
        else 
        {
            text.setText(item.toString());
        }

        return view;
    }

    public void setDropDownViewResource(int resource)
    {
        this.resource = resource;
    }
    
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return createViewFromResource(position, convertView, parent, dropDownResource);
    }
}
