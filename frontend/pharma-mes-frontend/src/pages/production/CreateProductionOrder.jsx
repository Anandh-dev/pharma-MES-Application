import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    Alert,
    Box,
    Button,
    CircularProgress,
    Paper,
    TextField,
    Typography
} from "@mui/material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";

import MainLayout from "../../layouts/MainLayout";

import {
    createProductionOrder
} from "../../services/productionService";

function CreateProductionOrder() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        orderNumber: "",
        batchNumber: "",
        materialId: "",
        plannedQuantity: "",
        unit: "",
        plannedStartDate: "",
        plannedEndDate: "",
        remarks: ""
    });

    const [loading, setLoading] = useState(false);

    const [error, setError] = useState("");

    const [validationError, setValidationError] =
        useState("");

    const handleChange = (event) => {

        const { name, value } = event.target;

        setFormData((previous) => ({
            ...previous,
            [name]: value
        }));

    };

    const handleSubmit = async (event) => {

        event.preventDefault();

        setError("");
        setValidationError("");

        // Frontend validation
        if (!formData.orderNumber.trim()) {
            setValidationError(
                "Order number is required."
            );
            return;
        }

        if (!formData.batchNumber.trim()) {
            setValidationError(
                "Batch number is required."
            );
            return;
        }

        if (!formData.materialId) {
            setValidationError(
                "Material ID is required."
            );
            return;
        }

        if (!formData.plannedQuantity) {
            setValidationError(
                "Planned quantity is required."
            );
            return;
        }

        if (
            Number(formData.plannedQuantity) <= 0
        ) {
            setValidationError(
                "Planned quantity must be greater than zero."
            );
            return;
        }

        if (!formData.unit.trim()) {
            setValidationError(
                "Unit is required."
            );
            return;
        }

        if (
            formData.plannedStartDate &&
            formData.plannedEndDate &&
            formData.plannedEndDate <
                formData.plannedStartDate
        ) {
            setValidationError(
                "Planned end date cannot be before planned start date."
            );
            return;
        }

        try {

            setLoading(true);

            const productionOrder = {
                orderNumber:
                    formData.orderNumber.trim(),

                batchNumber:
                    formData.batchNumber.trim(),

                materialId:
                    Number(formData.materialId),

                plannedQuantity:
                    Number(formData.plannedQuantity),

                unit:
                    formData.unit.trim(),

                plannedStartDate:
                    formData.plannedStartDate ||
                    null,

                plannedEndDate:
                    formData.plannedEndDate ||
                    null,

                remarks:
                    formData.remarks.trim() ||
                    null
            };

            console.log(
                "Creating production order:",
                productionOrder
            );

            const createdOrder =
                await createProductionOrder(
                    productionOrder
                );

            console.log(
                "Production order created:",
                createdOrder
            );

            navigate(
                `/production/${createdOrder.productionOrderId}`
            );

        } catch (error) {

            console.error(
                "Failed to create production order:",
                error.response?.data ||
                error.message
            );

            setError(
                error.response?.data?.message ||
                "Failed to create production order."
            );

        } finally {

            setLoading(false);

        }
    };

    return (
        <MainLayout>

            <Box>

                <Button
                    startIcon={<ArrowBackIcon />}
                    onClick={() =>
                        navigate("/production")
                    }
                    sx={{ mb: 2 }}
                >
                    Back to Production
                </Button>

                <Typography
                    variant="h4"
                    fontWeight="bold"
                    gutterBottom
                >
                    Create Production Order
                </Typography>

                <Typography
                    variant="body1"
                    color="text.secondary"
                    sx={{ mb: 3 }}
                >
                    Create a new manufacturing
                    production order.
                </Typography>

                <Paper
                    elevation={2}
                    sx={{
                        p: 4,
                        maxWidth: 900
                    }}
                >

                    {validationError && (

                        <Alert
                            severity="warning"
                            sx={{ mb: 3 }}
                        >
                            {validationError}
                        </Alert>

                    )}

                    {error && (

                        <Alert
                            severity="error"
                            sx={{ mb: 3 }}
                        >
                            {error}
                        </Alert>

                    )}

                    <Box
                        component="form"
                        onSubmit={handleSubmit}
                    >

                        <Box
                            sx={{
                                display: "grid",
                                gridTemplateColumns: {
                                    xs: "1fr",
                                    sm: "1fr 1fr"
                                },
                                gap: 3
                            }}
                        >

                            <TextField
                                label="Order Number"
                                name="orderNumber"
                                value={
                                    formData.orderNumber
                                }
                                onChange={handleChange}
                                required
                                fullWidth
                                placeholder="e.g. P0002"
                            />

                            <TextField
                                label="Batch Number"
                                name="batchNumber"
                                value={
                                    formData.batchNumber
                                }
                                onChange={handleChange}
                                required
                                fullWidth
                                placeholder="e.g. BATCH002"
                            />

                            <TextField
                                label="Material ID"
                                name="materialId"
                                value={
                                    formData.materialId
                                }
                                onChange={handleChange}
                                required
                                fullWidth
                                type="number"
                                slotProps={{
                                    htmlInput: {
                                        min: 1
                                    }
                                }}
                            />

                            <TextField
                                label="Planned Quantity"
                                name="plannedQuantity"
                                value={
                                    formData.plannedQuantity
                                }
                                onChange={handleChange}
                                required
                                fullWidth
                                type="number"
                                slotProps={{
                                    htmlInput: {
                                        min: 0,
                                        step: "any"
                                    }
                                }}
                            />

                            <TextField
                                label="Unit"
                                name="unit"
                                value={formData.unit}
                                onChange={handleChange}
                                required
                                fullWidth
                                placeholder="e.g. TABLETS"
                            />

                            <TextField
                                label="Planned Start Date"
                                name="plannedStartDate"
                                value={
                                    formData.plannedStartDate
                                }
                                onChange={handleChange}
                                fullWidth
                                type="date"
                                slotProps={{
                                    inputLabel: {
                                        shrink: true
                                    }
                                }}
                            />

                            <TextField
                                label="Planned End Date"
                                name="plannedEndDate"
                                value={
                                    formData.plannedEndDate
                                }
                                onChange={handleChange}
                                fullWidth
                                type="date"
                                slotProps={{
                                    inputLabel: {
                                        shrink: true
                                    }
                                }}
                            />

                            <TextField
                                label="Remarks"
                                name="remarks"
                                value={formData.remarks}
                                onChange={handleChange}
                                fullWidth
                                multiline
                                rows={3}
                                sx={{
                                    gridColumn: {
                                        xs: "auto",
                                        sm: "1 / -1"
                                    }
                                }}
                            />

                        </Box>

                        <Box
                            sx={{
                                display: "flex",
                                gap: 2,
                                mt: 4
                            }}
                        >

                            <Button
                                variant="outlined"
                                onClick={() =>
                                    navigate("/production")
                                }
                                disabled={loading}
                            >
                                Cancel
                            </Button>

                            <Button
                                type="submit"
                                variant="contained"
                                disabled={loading}
                            >
                                {loading ? (
                                    <>
                                        <CircularProgress
                                            size={20}
                                            sx={{ mr: 1 }}
                                        />
                                        Creating...
                                    </>
                                ) : (
                                    "Create Production Order"
                                )}
                            </Button>

                        </Box>

                    </Box>

                </Paper>

            </Box>

        </MainLayout>
    );
}

export default CreateProductionOrder;