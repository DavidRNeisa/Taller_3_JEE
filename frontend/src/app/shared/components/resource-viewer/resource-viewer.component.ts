import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Resource, ContentType } from '../../../models/lesson.model';
import { SanitizePipe } from '../../pipes/sanitize.pipe';

@Component({
  selector: 'app-resource-viewer',
  standalone: true,
  imports: [CommonModule, SanitizePipe],
  templateUrl: './resource-viewer.component.html',
  styleUrls: ['./resource-viewer.component.css'],
})
export class ResourceViewerComponent {
  @Input() resource!: Resource;

  getContentTypeLabel(type: ContentType): string {
    const labels: { [key in ContentType]: string } = {
      VIDEO: 'Video',
      PRESENTACION: 'Presentación',
      DOCUMENTO: 'Documento',
      PDF: 'PDF',
      ENLACE: 'Enlace',
      IMAGEN: 'Imagen',
      HTML: 'Contenido HTML',
    };
    return labels[type] || type;
  }

  getIcon(type: ContentType): string {
    const icons: { [key in ContentType]: string } = {
      VIDEO: '🎥',
      PRESENTACION: '📊',
      DOCUMENTO: '📄',
      PDF: '📑',
      ENLACE: '🔗',
      IMAGEN: '🖼️',
      HTML: '📱',
    };
    return icons[type] || '📎';
  }

  isEmbeddable(type: ContentType): boolean {
    return ['VIDEO', 'IMAGEN', 'PDF', 'HTML', 'PRESENTACION'].includes(type);
  }

  isPDF(type: ContentType): boolean {
    return type === 'PDF';
  }

  isImage(type: ContentType): boolean {
    return type === 'IMAGEN';
  }

  isVideo(type: ContentType): boolean {
    return type === 'VIDEO';
  }

  isHTML(type: ContentType): boolean {
    return type === 'HTML';
  }

  isLink(type: ContentType): boolean {
    return type === 'ENLACE';
  }

  isDocument(type: ContentType): boolean {
    return type === 'DOCUMENTO';
  }

  isPresentation(type: ContentType): boolean {
    return type === 'PRESENTACION';
  }

  getYouTubeEmbedUrl(url: string): string {
    let videoId = '';
    if (url.includes('youtu.be')) {
      videoId = url.split('youtu.be/')[1];
    } else if (url.includes('youtube.com')) {
      videoId = url.split('v=')[1]?.split('&')[0] || '';
    }
    return `https://www.youtube.com/embed/${videoId}`;
  }
}
