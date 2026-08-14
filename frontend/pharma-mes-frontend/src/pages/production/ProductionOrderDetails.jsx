import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Divider,
    Grid,
    TextField,
    Typography
} from "@mui/material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";

import MainLayout from "../../layouts/MainLayout";

import {
    getProductionOrderById,
    releaseProductionOrder,
    reserveMaterials,
    startProduction,
    completeProduction,
    closeProductionOrder,
    cancelProductionOrder
} from "../../services/productionService";

function ProductionOrderDetails() {

    const { id } = useParams();

    const navigate = useNavigate();

    const [productionOrder, setProductionOrder] =
        useState(null);

    const [loading, setLoading] =
        useState(true);

    const [error, setError] =
        useState("");

    const [workflowLoading, setWorkflowLoading] =
        useState(false);

    const [workflowError, setWorkflowError] =
        useState("");

    const [completeDialogOpen, setCompleteDialogOpen] =
        useState(false);

    const [producedQuantity, setProducedQuantity] =
        useState("");

    useEffect(() => {

        const loadProductionOrder = async () => {

            try {

                setLoading(true);
                setError("");

                const data =
                    await getProductionOrderById(id);

                console.log(
                    "Production order details loaded:",
                    data
                );

                setProductionOrder(data);

            } catch (error) {

                console.error(
                    "Failed to load production order:",
                    error.response?.data ||
                    error.message
                );

                setError(
                    "Unable to load production order details."
                );

            } finally {

                setLoading(false);

            }
        };

        loadProductionOrder();

    }, [id]);

    const handleWorkflowAction = async (action) => {

        try {

            setWorkflowLoading(true);
            setWorkflowError("");

            let updatedOrder;

            switch (action) {

                case "RELEASE":

                    updatedOrder =
                        await releaseProductionOrder(id);

                    break;

                case "RESERVE":

                    updatedOrder =
                        await reserveMaterials(id);

                    break;

                case "START":

                    updatedOrder =
                        await startProduction(id);

                    break;

                case "CLOSE":

                    updatedOrder =
                        await closeProductionOrder(id);

                    break;

                case "CANCEL":

                    updatedOrder =
                        await cancelProductionOrder(id);

                    break;

                default:
                    return;
            }

            console.log(
                "Production workflow action successful:",
                updatedOrder
            );

            setProductionOrder(updatedOrder);

        } catch (error) {

            console.error(
                "Production workflow action failed:",
                error.response?.data ||
                error.message
            );

            setWorkflowError(
                error.response?.data?.message ||
                "Production workflow action failed."
            );

        } finally {

            setWorkflowLoading(false);

        }
    };

    const handleComplete = async () => {

        try {

            setWorkflowLoading(true);
            setWorkflowError("");

            const updatedOrder =
                await completeProduction(
                    id,
                    Number(producedQuantity)
                );

            console.log(
                "Production order completed:",
                updatedOrder
            );

            setProductionOrder(updatedOrder);

            setCompleteDialogOpen(false);

            setProducedQuantity("");

        } catch (error) {

            console.error(
                "Failed to complete production:",
                error.response?.data ||
                error.message
            );

            setWorkflowError(
                error.response?.data?.message ||
                "Failed to complete production."
            );

        } finally {

            setWorkflowLoading(false);

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
                    Production Order Details
                </Typography>

                {loading && (

                    <Box
                        sx={{
                            display: "flex",
                            justifyContent: "center",
                            mt: 5
                        }}
                    >
                        <CircularProgress />
                    </Box>

                )}

                {error && (

                    <Alert
                        severity="error"
                        sx={{ mt: 2 }}
                    >
                        {error}
                    </Alert>

                )}

                {!loading &&
                    !error &&
                    productionOrder && (

                    <Card
                        elevation={2}
                        sx={{ mt: 3 }}
                    >

                        <CardContent>

                            <Typography
                                variant="h5"
                                fontWeight="bold"
                                gutterBottom
                            >
                                {
                                    productionOrder.orderNumber
                                }
                            </Typography>

                            <Divider
                                sx={{ mb: 3 }}
                            />

                            <Grid
                                container
                                spacing={3}
                            >

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Production Order ID
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .productionOrderId
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Order Number
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .orderNumber
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Batch Number
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .batchNumber
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Material ID
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .materialId
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Status
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .status
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Unit
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder.unit
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Planned Quantity
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .plannedQuantity
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Produced Quantity
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .producedQuantity ??
                                            "-"
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Planned Start
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .plannedStartDate ??
                                            "-"
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Planned End
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .plannedEndDate ??
                                            "-"
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Actual Start
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .actualStartTime ??
                                            "-"
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Actual End
                                    </Typography>

                                    <Typography variant="h6">
                                        {
                                            productionOrder
                                                .actualEndTime ??
                                            "-"
                                        }
                                    </Typography>
                                </Grid>

                                <Grid
                                    size={{ xs: 12 }}
                                >
                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Remarks
                                    </Typography>

                                    <Typography variant="body1">
                                        {
                                            productionOrder
                                                .remarks ||
                                            "-"
                                        }
                                    </Typography>
                                </Grid>

                            </Grid>

                        </CardContent>

                        {/* Workflow */}

                        <CardContent>

                            <Typography
                                variant="h6"
                                fontWeight="bold"
                                gutterBottom
                            >
                                Production Workflow
                            </Typography>

                            {workflowError && (

                                <Alert
                                    severity="error"
                                    sx={{ mb: 2 }}
                                >
                                    {workflowError}
                                </Alert>

                            )}

                            <Box
                                sx={{
                                    display: "flex",
                                    gap: 2,
                                    flexWrap: "wrap"
                                }}
                            >

                                {productionOrder.status ===
                                    "CREATED" && (

                                    <Button
                                        variant="contained"
                                        disabled={
                                            workflowLoading
                                        }
                                        onClick={() =>
                                            handleWorkflowAction(
                                                "RELEASE"
                                            )
                                        }
                                    >
                                        Release Order
                                    </Button>

                                )}

                                {productionOrder.status ===
                                    "RELEASED" && (

                                    <Button
                                        variant="contained"
                                        disabled={
                                            workflowLoading
                                        }
                                        onClick={() =>
                                            handleWorkflowAction(
                                                "RESERVE"
                                            )
                                        }
                                    >
                                        Reserve Materials
                                    </Button>

                                )}

                                {productionOrder.status ===
                                    "MATERIAL_RESERVED" && (

                                    <Button
                                        variant="contained"
                                        disabled={
                                            workflowLoading
                                        }
                                        onClick={() =>
                                            handleWorkflowAction(
                                                "START"
                                            )
                                        }
                                    >
                                        Start Production
                                    </Button>

                                )}

                                {productionOrder.status ===
                                    "IN_PROGRESS" && (

                                    <Button
                                        variant="contained"
                                        disabled={
                                            workflowLoading
                                        }
                                        onClick={() =>
                                            setCompleteDialogOpen(
                                                true
                                            )
                                        }
                                    >
                                        Complete Production
                                    </Button>

                                )}

                                {productionOrder.status ===
                                    "COMPLETED" && (

                                    <Button
                                        variant="contained"
                                        disabled={
                                            workflowLoading
                                        }
                                        onClick={() =>
                                            handleWorkflowAction(
                                                "CLOSE"
                                            )
                                        }
                                    >
                                        Close Production Order
                                    </Button>

                                )}

                                {[
                                    "CREATED",
                                    "RELEASED",
                                    "MATERIAL_RESERVED",
                                    "IN_PROGRESS"
                                ].includes(
                                    productionOrder.status
                                ) && (

                                    <Button
                                        color="error"
                                        variant="outlined"
                                        disabled={
                                            workflowLoading
                                        }
                                        onClick={() =>
                                            handleWorkflowAction(
                                                "CANCEL"
                                            )
                                        }
                                    >
                                        Cancel Order
                                    </Button>

                                )}

                            </Box>

                        </CardContent>

                    </Card>

                )}

            </Box>

            {/* Complete Production Dialog */}

            <Dialog
                open={completeDialogOpen}
                onClose={() => {

                    if (!workflowLoading) {
                        setCompleteDialogOpen(
                            false
                        );
                    }

                }}
            >

                <DialogTitle>
                    Complete Production
                </DialogTitle>

                <DialogContent>

                    <DialogContentText
                        sx={{ mb: 2 }}
                    >
                        Enter the actual quantity
                        produced for{" "}
                        <strong>
                            {
                                productionOrder?.orderNumber
                            }
                        </strong>.
                    </DialogContentText>

                    <TextField
                        fullWidth
                        required
                        autoFocus
                        type="number"
                        label="Produced Quantity"
                        value={producedQuantity}
                        onChange={(event) =>
                            setProducedQuantity(
                                event.target.value
                            )
                        }
                        slotProps={{
                            htmlInput: {
                                min: 0,
                                step: "any"
                            }
                        }}
                    />

                </DialogContent>

                <DialogActions>

                    <Button
                        onClick={() =>
                            setCompleteDialogOpen(
                                false
                            )
                        }
                        disabled={workflowLoading}
                    >
                        Cancel
                    </Button>

                    <Button
                        variant="contained"
                        onClick={handleComplete}
                        disabled={
                            workflowLoading ||
                            !producedQuantity
                        }
                    >
                        {workflowLoading
                            ? "Completing..."
                            : "Complete Production"}
                    </Button>

                </DialogActions>

            </Dialog>

        </MainLayout>
    );
}

export default ProductionOrderDetails;