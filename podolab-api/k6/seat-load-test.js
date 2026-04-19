import http from 'k6/http';

export const options = {
    vus: 100,        // 동시 유저 100명
    duration: '10s', // 10초 동안
};

export default function () {
    http.get('http://54.116.24.182/api/concerts/1/seats');
}