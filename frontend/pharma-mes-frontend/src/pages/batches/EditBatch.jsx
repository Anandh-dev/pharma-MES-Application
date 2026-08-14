import { useEffect, useState } from "react";

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

import { useNavigate, useParams } from "react-router-dom";

import MainLayout from "../../layouts/MainLayout";

import {
    getBatchById,
    updateBatch
} from "../../services/batchService";

function EditBatch() {

    const { id } = useParams();

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        batchNumber: "",
        productionOrderId: "",
        recipeId: "",
        plannedQuantity: "",
        remarks: ""
    });

    const [loading, setLoading] = useState(true);

    const [saving, setSaving] = useState(false);

    const [error, setError] = useState("");

    useEffect(() => {

        const loadBatch = async () => {

            try {

                setLoading(true);

                const data = await getBatchById(id);

                console.log(
                    "Batch for editing:",
                    data
                );

                setFormData({
                    batchNumber: data.batchNumber || "",
                    productionOrderId:
                        data.productionOrderId || "",
                    recipeId:
                        data.recipeId || "",
                    plannedQuantity:
                        data.plannedQuantity || "",
                    remarks:
                        data.remarks || ""
                });

            } catch (error) {

                console.error(
                    "Failed to load batch:",
                    error.response?.data ||
                    error.message
                );

                setError(
                    "Unable to load batch."
                );

            } finally {

                setLoading(false);

            }
        };

        loadBatch();

    }, [id]);

    const handleChange = (event) => {

        const { name, value } = event.target;

        setFormData((previous) => ({
            ...previous,
            [name]: value
        }));

    };

    const handleSubmit = async (event) => {

        event.preventDefault();

        try {

            setSaving(true);

            setError("");

            const requestData = {
                batchNumber:
                    formData.batchNumber,

                productionOrderId:
                    Number(formData.productionOrderId),

                recipeId:
                    Number(formData.recipeId),

                plannedQuantity:
                    Number(formData.plannedQuantity),

                remarks:
                    formData.remarks || null
            };

            console.log(
                "Updating batch:",
                requestData
            );

            await updateBatch(
                id,
                requestData
            );

            navigate(`/batches/${id}`);

        } catch (error) {

            console.error(
                "Failed to update batch:",
                error.response?.data ||
                error.message
            );

            setError(
                error.response?.data?.message ||
                "Failed to update batch."
            );

        } finally {

            setSaving(false);

        }
    };

    if (loading) {

        return (
            <MainLayout>

                <Typography variant="h5">
                    Loading batch...
                </Typography>

            </MainLayout>
        );

    }

    return (
        <MainLayout>

            <Box>

                <Button
                    startIcon={<ArrowBackIcon />}
                    onClick={() =>
                        navigate(`/batches/${id}`)
                    }
                    sx={{ mb: 2 }}
                >
                    Back to Batch
                </Button>

                <Typography
                    variant="h4"
                    fontWeight="bold"
                    gutterBottom
                >
                    Edit Batch
                </Typography>

                <Typography
                    color="text.secondary"
                    sx={{ mb: 3 }}
                >
                    Update batch information.
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
                                        onChange={
                                            handleChange
                                        }
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
                                        onChange={
                                            handleChange
                                        }
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
                                        onChange={
                                            handleChange
                                        }
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
                                        onChange={
                                            handleChange
                                        }
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
                                        onChange={
                                            handleChange
                                        }
                                    />

                                </Grid>

                                <Grid size={12}>

                                    <Box
                                        sx={{
                                            display: "flex",
                                            justifyContent:
                                                "flex-end",
                                            gap: 2
                                        }}
                                    >

                                        <Button
                                            variant="outlined"
                                            onClick={() =>
                                                navigate(
                                                    `/batches/${id}`
                                                )
                                            }
                                        >
                                            Cancel
                                        </Button>

                                        <Button
                                            type="submit"
                                            variant="contained"
                                            disabled={saving}
                                        >
                                            {saving
                                                ? "Saving..."
                                                : "Save Changes"}
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

export default EditBatch;