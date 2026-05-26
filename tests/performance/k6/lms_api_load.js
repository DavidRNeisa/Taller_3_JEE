import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate, Trend } from 'k6/metrics';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const alumnoId = __ENV.ALUMNO_ID || '1';
const cursoId = __ENV.CURSO_ID || '1';
const claseId = __ENV.CLASE_ID || '1';

export const errorRate = new Rate('http_errors');
export const latencyTrend = new Trend('http_latency_ms');

export const options = {
  scenarios: {
    warmup: {
      executor: 'constant-vus',
      vus: 10,
      duration: '30s',
      tags: { phase: 'warmup' }
    },
    ramp: {
      executor: 'ramping-vus',
      startVUs: 20,
      stages: [
        { duration: '1m', target: 50 },
        { duration: '1m', target: 100 },
        { duration: '1m', target: 150 },
        { duration: '1m', target: 200 },
        { duration: '30s', target: 0 }
      ],
      tags: { phase: 'ramp' },
      startTime: '30s'
    }
  },
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<600', 'p(99)<1200'],
    http_errors: ['rate<0.01']
  }
};

function hit(name, path) {
  const res = http.get(`${BASE_URL}${path}`, { tags: { name } });
  latencyTrend.add(res.timings.duration);
  const ok = check(res, {
    [`${name} status 2xx`]: (r) => r.status >= 200 && r.status < 300
  });
  errorRate.add(!ok);
}

export default function () {
  hit('GET_cursos', '/api/cursos');
  hit('GET_curso_id', `/api/cursos/${cursoId}`);
  hit('GET_clases_por_curso', `/api/clases/curso/${cursoId}`);
  hit('GET_contenidos_por_clase', `/api/contenidos/clase/${claseId}`);
  hit('GET_tareas_por_clase', `/api/tareas/clase/${claseId}`);
  hit('GET_calificaciones_alumno', `/api/calificaciones/${alumnoId}`);
  hit('GET_recomendaciones_alumno', `/api/recomendaciones/${alumnoId}`);
  sleep(1);
}
