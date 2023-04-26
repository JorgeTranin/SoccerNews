package com.soccernews.ui.news.adapternews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soccernews.databinding.ItemNewsBinding;
import com.soccernews.domain.News;
import com.squareup.picasso.BuildConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    //Lista de noticias
    private List<News> news;

    public NewsAdapter(List<News> news) {
        this.news = news;
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
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    news.getLinkNews() + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");

            // Adiciona opções de compartilhamento
            Intent whatsappIntent = new Intent();
            whatsappIntent.setAction(Intent.ACTION_SEND);
            whatsappIntent.putExtra(Intent.EXTRA_TEXT,
                    news.getLinkNews() + BuildConfig.APPLICATION_ID);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");

            Intent otherIntent = new Intent();
            otherIntent.setAction(Intent.ACTION_CHOOSER);
            otherIntent.putExtra(Intent.EXTRA_INTENT, sendIntent);
            otherIntent.putExtra(Intent.EXTRA_TITLE, "Compartilhar via...");

            Intent[] intents = {whatsappIntent};
            otherIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);

            holder.itemView.getContext().startActivity(otherIntent);
        });

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
}
