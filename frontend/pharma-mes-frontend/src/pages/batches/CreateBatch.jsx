import { useState } from "react";

import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    Grid,
    TextField,
    Typography
} from "@mui/material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";

import { useNavigate } from "react-router-dom";

import MainLayout from "../../layouts/MainLayout";

import { createBatch } from "../../services/batchService";

function CreateBatch() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        batchNumber: "",
        productionOrderId: "",
        recipeId: "",
        plannedQuantity: "",
        remarks: ""
    });

    const [error, setError] = useState("");

    const [loading, setLoading] = useState(false);

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

        try {

            setLoading(true);

            const requestData = {
                batchNumber: formData.batchNumber,
                productionOrderId: Number(
                    formData.productionOrderId
                ),
                recipeId: Number(
                    formData.recipeId
                ),
                plannedQuantity: Number(
                    formData.plannedQuantity
                ),
                remarks: formData.remarks || null
            };

            console.log(
                "Creating batch:",
                requestData
            );

            await createBatch(requestData);

            navigate("/batches");

        } catch (error) {

            console.error(
                "Failed to create batch:",
                error.response?.data || error.message
            );

            setError(
                error.response?.data?.message ||
                "Failed to create batch."
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
                    onClick={() => navigate("/batches")}
                    sx={{ mb: 2 }}
                >
                    Back to Batches
                </Button>

                <Typography
                    variant="h4"
                    fontWeight="bold"
                    gutterBottom
                >
                    Create Batch
                </Typography>

                <Typography
                    color="text.secondary"
                    sx={{ mb: 3 }}
                >
                    Create a new manufacturing batch.
                </Typography>

                {error && (
                    <Alert
                        severity="error"
                        sx={{ mb: 3 }}
                    >
                        {error}
                    </Alert>
                )}

                <Card elevation={2}>

                    <CardContent>

                        <Box
                            component="form"
                            onSubmit={handleSubmit}
                        >

                            <Grid
                                container
                                spacing={3}
                            >

                                <Grid
                                    size={{
                                        xs: 12,
                                        md: 6
                                    }}
                                >

                                    <TextField
                                        fullWidth
                                        required
                                        label="Batch Number"
                                        name="batchNumber"
                                        value={
                                            formData.batchNumber
                                        }
                                        onChange={handleChange}
                                    />

                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        md: 6
                                    }}
                                >

                                    <TextField
                                        fullWidth
                                        required
                                        type="number"
                                        label="Production Order ID"
                                        name="productionOrderId"
                                        value={
                                            formData.productionOrderId
                                        }
                                        onChange={handleChange}
                                    />

                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        md: 6
                                    }}
                                >

                                    <TextField
                                        fullWidth
                                        required
                                        type="number"
                                        label="Recipe ID"
                                        name="recipeId"
                                        value={
                                            formData.recipeId
                                        }
                                        onChange={handleChange}
                                    />

                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        md: 6
                                    }}
                                >

                                    <TextField
                                        fullWidth
                                        required
                                        type="number"
                                        label="Planned Quantity"
                                        name="plannedQuantity"
                                        value={
                                            formData.plannedQuantity
                                        }
                                        onChange={handleChange}
                                        slotProps={{
                                            htmlInput: {
                                                min: 0.01,
                                                step: "any"
                                            }
                                        }}
                                    />

                                </Grid>

                                <Grid size={12}>

                                    <TextField
                                        fullWidth
                                        multiline
                                        rows={4}
                                        label="Remarks"
                                        name="remarks"
                                        value={
                                            formData.remarks
                                        }
                                        onChange={handleChange}
                                    />

                                </Grid>

                                <Grid size={12}>

                                    <Box
                                        sx={{
                                            display: "flex",
                                            gap: 2,
                                            justifyContent: "flex-end"
                                        }}
                                    >

                                        <Button
                                            variant="outlined"
                                            onClick={() =>
                                                navigate(
                                                    "/batches"
                                                )
                                            }
                                        >
                                            Cancel
                                        </Button>

                                        <Button
                                            type="submit"
                                            variant="contained"
                                            disabled={loading}
                                        >
                                            {loading
                                                ? "Creating..."
                                                : "Create Batch"}
                                        </Button>

                                    </Box>

                                </Grid>

                            </Grid>

                        </Box>

                    </CardContent>

                </Card>

            </Box>

        </MainLayout>
    );
}

export default CreateBatch;