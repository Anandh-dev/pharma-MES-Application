import axios from "axios";

const API_URL = "http://localhost:8080";

const login = async (username, password) => {
    const response = await axios.post(
        `${API_URL}/auth/login`,
        {
            username,
            password
        }
    );

    const data = response.data;

    localStorage.setItem("accessToken", data.accessToken);
    localStorage.setItem("tokenType", data.tokenType);
    localStorage.setItem("username", data.username);
    localStorage.setItem("role", data.role);

    return data;
};

const logout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("tokenType");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
};

const getToken = () => {
    return localStorage.getItem("accessToken");
};

const isAuthenticated = () => {
    return !!getToken();
};

export default {
    login,
    logout,
    getToken,
    isAuthenticated
};