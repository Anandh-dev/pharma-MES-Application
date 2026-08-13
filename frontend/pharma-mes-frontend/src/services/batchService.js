import api from "./api";

export const getAllBatches = async () => {

    const response = await api.get("/api/batches");

    return response.data;
};

export default {
    getAllBatches
};