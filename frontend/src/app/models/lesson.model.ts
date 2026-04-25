export type ContentType = 'VIDEO' | 'PRESENTACION' | 'DOCUMENTO' | 'PDF' | 'ENLACE' | 'IMAGEN' | 'HTML';

export interface Resource {
  id: number;
  type: ContentType;
  title: string;
  url: string;
  order: number;
}

export interface Lesson {
  id: number;
  courseId: number;
  title: string;
  description: string;
  order: number;
  progress: number; // 0-100
  completed: boolean;
  resources: Resource[];
  hasAssignments: boolean;
  assignmentsDue?: string; // ISO date
}
