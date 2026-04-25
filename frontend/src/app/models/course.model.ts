export interface Course {
  id: number;
  title: string;
  description: string;
  lessonsCount: number;
  progress: number; // 0-100
  enrolled: boolean;
}
