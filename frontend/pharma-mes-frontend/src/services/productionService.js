import api from "./api";

const BASE_URL = "/api/production-orders";

// Get all production orders
export const getAllProductionOrders = async () => {
    const response = await api.get(BASE_URL);
    return response.data;
};

// Get production order by ID
export const getProductionOrderById = async (id) => {
    const response = await api.get(`${BASE_URL}/${id}`);
    return response.data;
};

// Create production order
export const createProductionOrder = async (productionOrder) => {
    const response = await api.post(BASE_URL, productionOrder);
    return response.data;
};

// Update production order
export const updateProductionOrder = async (id, productionOrder) => {
    const response = await api.put(
        `${BASE_URL}/${id}`,
        productionOrder
    );
    return response.data;
};

// Delete production order
export const deleteProductionOrder = async (id) => {
    await api.delete(`${BASE_URL}/${id}`);
};

// Cancel production order
export const cancelProductionOrder = async (id) => {
    const response = await api.put(
        `${BASE_URL}/${id}/cancel`
    );

    return response.data;
};

// Release production order
export const releaseProductionOrder = async (id) => {
    const response = await api.put(
        `${BASE_URL}/${id}/release`
    );

    return response.data;
};

// Reserve materials
export const reserveMaterials = async (id) => {
    const response = await api.put(
        `${BASE_URL}/${id}/reserve-materials`
    );

    return response.data;
};

// Start production
export const startProduction = async (id) => {
    const response = await api.put(
        `${BASE_URL}/${id}/start`
    );

    return response.data;
};

// Complete production
export const completeProduction = async (
    id,
    producedQuantity
) => {
    const response = await api.put(
        `${BASE_URL}/${id}/complete`,
        null,
        {
            params: {
                producedQuantity
            }
        }
    );

    return response.data;
};

// Close production order
export const closeProductionOrder = async (id) => {
    const response = await api.put(
        `${BASE_URL}/${id}/close`
    );

    return response.data;
};

// Search by order number
export const getByOrderNumber = async (orderNumber) => {
    const response = await api.get(
        `${BASE_URL}/order/${orderNumber}`
    );
    return response.data;
};

// Search by batch number
export const getByBatchNumber = async (batchNumber) => {
    const response = await api.get(
        `${BASE_URL}/batch/${batchNumber}`
    );
    return response.data;
};

// Get by status
export const getByStatus = async (status) => {
    const response = await api.get(
        `${BASE_URL}/status/${status}`
    );
    return response.data;
};

// Get by material
export const getByMaterial = async (materialId) => {
    const response = await api.get(
        `${BASE_URL}/material/${materialId}`
    );
    return response.data;
};

// Get by planned date
export const getByPlannedDate = async (plannedDate) => {
    const response = await api.get(
        `${BASE_URL}/planned-date/${plannedDate}`
    );
    return response.data;
};

// Get by date range
export const getByDateRange = async (startDate, endDate) => {
    const response = await api.get(
        `${BASE_URL}/date-range`,
        {
            params: {
                startDate,
                endDate
            }
        }
    );

    return response.data;
};

// Get paginated production orders
export const getProductionOrderPage = async (
    page = 0,
    size = 10,
    sortBy = "productionOrderId"
) => {
    const response = await api.get(
        `${BASE_URL}/page`,
        {
            params: {
                page,
                size,
                sortBy
            }
        }
    );

    return response.data;
};