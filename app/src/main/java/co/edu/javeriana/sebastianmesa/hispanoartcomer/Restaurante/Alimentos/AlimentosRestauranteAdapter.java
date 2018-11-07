package co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.Alimentos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.threeten.bp.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import co.edu.javeriana.sebastianmesa.hispanoartcomer.R;
import co.edu.javeriana.sebastianmesa.hispanoartcomer.Restaurante.RestauranteAditivosView;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlimentosRestauranteAdapter extends RecyclerView.Adapter<AlimentosRestauranteAdapter.MyViewHolder> implements Serializable {

    private Context mContext;
    private ArrayList<AlimentosMenu> listaAlimentos;
    private ArrayList<AlimentosMenu> listaAlimentosCompleta;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    private int[] covers = new int[]{
            R.drawable.ic_food_1,
            R.drawable.ic_food_2,
            R.drawable.ic_food_3,
            R.drawable.ic_food_4,
            R.drawable.ic_food_5,
            R.drawable.ic_food_6,
            R.drawable.ic_food_7,
            R.drawable.ic_food_8,
            R.drawable.ic_food_9,
            R.drawable.ic_food_10,
            R.drawable.ic_food_11,
            R.drawable.ic_food_12,
            R.drawable.ic_food_13,
            R.drawable.ic_food_14,
            R.drawable.ic_food_15,
            R.drawable.ic_food_16,
            R.drawable.ic_food_17,
            R.drawable.ic_food_18,
            R.drawable.ic_food_19,
            R.drawable.ic_food_21,
            R.drawable.ic_food_22,
            R.drawable.ic_food_23,
            R.drawable.ic_food_24,
            R.drawable.ic_food_25,
            R.drawable.ic_food_26,
            R.drawable.ic_food_27,
            R.drawable.ic_food_28,
            R.drawable.ic_food_29,
            R.drawable.ic_food_30,
            R.drawable.ic_food_31,
            R.drawable.ic_food_32,
            R.drawable.ic_food_33,
            R.drawable.ic_food_34,
            R.drawable.ic_food_35,
            R.drawable.ic_food_36,
            R.drawable.ic_food_37,
            R.drawable.ic_food_38,
            R.drawable.ic_food_39,
            R.drawable.ic_food_41,
            R.drawable.ic_food_42,
            R.drawable.ic_food_43,
            R.drawable.ic_food_44,
            R.drawable.ic_food_45,
            R.drawable.ic_food_46,
            R.drawable.ic_food_47,
            R.drawable.ic_food_48,
            R.drawable.ic_food_49};


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);

            overflow.setVisibility(View.GONE);
        }


    }


    public AlimentosRestauranteAdapter(Context mContext, ArrayList<AlimentosMenu> listaAlimentos, ArrayList<AlimentosMenu> listaAlimentosCompleta) {
        this.mContext = mContext;
        this.listaAlimentos = listaAlimentos;
        this.listaAlimentosCompleta = listaAlimentosCompleta;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        AlimentosMenu alimentosMenu = listaAlimentos.get(position);
        Random rand = new Random();

        if (listaAlimentos.get(position).getTipo().equals("almuerzo") ||
                listaAlimentos.get(position).getTipo() == "almuerzo"){
            holder.title.setText(listaAlimentos.get(position).getPlatoPrincipal());
            holder.count.setText("Almuerzo");
            int  n = rand.nextInt(44) + 0;
            holder.thumbnail.setBackgroundResource(covers[n]);



        }
        if (listaAlimentos.get(position).getTipo().equals("nueves") ||
                listaAlimentos.get(position).getTipo() == "nueves"){
            holder.title.setText(listaAlimentos.get(position).getPlato());
            holder.count.setText("Nueves");
            int  n = rand.nextInt(44) + 0;
            holder.thumbnail.setBackgroundResource(covers[n]);
        }
        if (listaAlimentos.get(position).getTipo().equals("onces") ||
                listaAlimentos.get(position).getTipo() == "onces"){
            holder.title.setText(listaAlimentos.get(position).getPlato());
            holder.count.setText("Onces");
            int  n = rand.nextInt(44) + 0;
            holder.thumbnail.setBackgroundResource(covers[n]);
        }

        Glide.with(mContext).load(alimentosMenu.getThumbnail()).into(holder.thumbnail);

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //showPopupMenu(holder.overflow);
//            }
//        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fecha = (FORMATTER.format(listaAlimentos.get(position).getFecha()));

                Intent intent = new Intent(mContext, RestauranteAditivosView.class);

                if (listaAlimentos.get(position).getTipo().equals("almuerzo") ||
                        listaAlimentos.get(position).getTipo() == "almuerzo"){

                    intent.putExtra("typeMenu", listaAlimentos.get(position).getTipo());
                    intent.putExtra("seleccionNum",position );
                    intent.putExtra("nomPlatoPrincipal", listaAlimentos.get(position).getPlatoPrincipal());
                    intent.putExtra("nomPlatoSecundario", listaAlimentos.get(position).getPlatoSegundario());
                    intent.putExtra("nomPlatoPostre", listaAlimentos.get(position).getPlatoPostre());
                    intent.putExtra("nomBebida", listaAlimentos.get(position).getBebidaAlmuerzo());
                    intent.putExtra("listaDeAditivos", (Serializable) listaAlimentos.get(position).getAditivos() );
                    intent.putExtra("fechaAlimento", fecha);
                    mContext.startActivity(intent);
                }

                if (listaAlimentos.get(position).getTipo().equals("nueves") ||
                        listaAlimentos.get(position).getTipo() == "nueves"){

                    intent.putExtra("typeMenu", listaAlimentos.get(position).getTipo());
                    intent.putExtra("seleccionNum",position );
                    intent.putExtra("nomPlatoNueves", listaAlimentos.get(position).getPlato());
                    intent.putExtra("nomBebidaNueves", listaAlimentos.get(position).getBebida());
                    intent.putExtra("listaDeAditivos", (Serializable) listaAlimentos.get(position).getAditivos() );
                    intent.putExtra("fechaAlimento", fecha);
                    mContext.startActivity(intent);

                }

                if (listaAlimentos.get(position).getTipo().equals("onces") ||
                        listaAlimentos.get(position).getTipo() == "onces"){

                    intent.putExtra("typeMenu", listaAlimentos.get(position).getTipo());
                    intent.putExtra("seleccionNum",position );
                    intent.putExtra("nomPlatoOnces", listaAlimentos.get(position).getPlato());
                    intent.putExtra("nomBebidaOnces", listaAlimentos.get(position).getBebida());
                    intent.putExtra("listaDeAditivos", (Serializable) listaAlimentos.get(position).getAditivos() );
                    intent.putExtra("fechaAlimento", fecha);
                    mContext.startActivity(intent);

                }

            }
        });
    }



    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return listaAlimentos.size();
    }


}