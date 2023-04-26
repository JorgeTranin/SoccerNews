package com.soccernews.ui.news.adapternews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soccernews.R;
import com.soccernews.databinding.ItemNewsBinding;
import com.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    //Lista de noticias
    private List<News> news;
    private NewsListener favoriteListener;

    public NewsAdapter(List<News> news, NewsListener favoriteListener) {
        this.news = news;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNewsBinding binding = ItemNewsBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = this.news.get(position);
        Context context = holder.itemView.getContext();

        //Set Imagem Times
        ImageView imageNew = (ImageView) holder.binding.ivNew;

        Picasso.get().load(news.getImage())
                .into(imageNew);

        holder.binding.tvTitleNew.setText(news.getTitle());
        holder.binding.tvDescripition.setText(news.getDescription());
        holder.binding.tvTextNew.setText(news.getTextNews());

        //Abrir a news no navegador do dispositivo
        holder.binding.btnOpenLink.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.getLinkNews()));
            holder.itemView.getContext().startActivity(i);
        });

        //Intent de compartilhamento da noticia
        holder.binding.ivCompartilhar.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, news.getLinkNews());
            holder.itemView.getContext().startActivity(Intent.createChooser(sendIntent, "Share"));

        });
        // Itent de click no icone favoritos e sera iniciado pelo Fragments

        holder.binding.ivFavorite.setOnClickListener(view -> {
            news.favorite = !news.favorite;
            this.favoriteListener.onClick(news);
            //notificando que o elemento foi atualizado e o recyclerView atualize a lista
            notifyItemChanged(position);
        });
        if (news.favorite) {
            holder.binding.ivFavorite.setColorFilter(context.getResources().getColor(R.color.Red));
        }

    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewsBinding binding;

        public ViewHolder(ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface NewsListener {
        void onClick(News news);
    }
}
