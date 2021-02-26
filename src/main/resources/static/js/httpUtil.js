const http = axios.create({
    //超时时间 三分钟
    timeout: 1000 * 60 * 3,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
    }
});

//响应拦截器
http.interceptors.response.use(
    response => {
        if (response.status === 200) {
            if (response.data.code === 0) {
                //对返回成功码的直接返回数据
                return Promise.resolve(response.data);
            } else {
                return Promise.resolve(response);
            }
        } else {
            return Promise.reject(response);
        }
    },
    error => {
        //Toast.clear();
        console.log('系统异常(system error)');
        return Promise.reject(error.response);
    }
);

//异步请求
function get(url, params,msg) {
    return new Promise(((resolve, reject) => {
            http.get(url, {params: params}).then(response => {
                resolve(response.data)
            }).catch(err => {
                if (msg){
                    msg();
                }
                reject(err)
            })
        })
    )
}

//异步请求
function post(url, params,msg) {
    return new Promise(((resolve, reject) => {
            http.post(url, params).then(response => {
                resolve(response.data)
            }).catch(err => {
                if (msg){
                    msg();
                }
                reject(err)
            })
        })
    )
}
