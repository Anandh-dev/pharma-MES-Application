import api from "./api";

export const getAllBatches = async () => {
    const response = await api.get("/api/batches");
    return response.data;
};

export const getBatchById = async (id) => {
    const response = await api.get(`/api/batches/${id}`);
    return response.data;
};

export const createBatch = async (batchData) => {
    const response = await api.post(
        "/api/batches",
        batchData
    );

    return response.data;
};

export const updateBatch = async (id, batchData) => {
    const response = await api.put(
        `/api/batches/${id}`,
        batchData
    );

    return response.data;
};

export const deleteBatch = async (id) => {
    await api.delete(`/api/batches/${id}`);
};

export const markBatchReady = async (id) => {
    const response = await api.put(
        `/api/batches/${id}/ready`
    );

    return response.data;
};

export const startBatch = async (id) => {
    const response = await api.put(
        `/api/batches/${id}/start`
    );

    return response.data;
};

export const holdBatch = async (id) => {
    const response = await api.put(
        `/api/batches/${id}/hold`
    );

    return response.data;
};

export const resumeBatch = async (id) => {
    const response = await api.put(
        `/api/batches/${id}/resume`
    );

    return response.data;
};

export const completeBatch = async (
    id,
    actualQuantity
) => {
    const response = await api.put(
        `/api/batches/${id}/complete`,
        null,
        {
            params: {
                actualQuantity
            }
        }
    );

    return response.data;
};

export const closeBatch = async (id) => {
    const response = await api.put(
        `/api/batches/${id}/close`
    );

    return response.data;
};

export const cancelBatch = async (id) => {
    const response = await api.put(
        `/api/batches/${id}/cancel`
    );

    return response.data;
};

export default {
    getAllBatches,
    getBatchById,
    createBatch,
    updateBatch,
    deleteBatch,
    markBatchReady,
    startBatch,
    holdBatch,
    resumeBatch,
    completeBatch,
    closeBatch,
    cancelBatch
};