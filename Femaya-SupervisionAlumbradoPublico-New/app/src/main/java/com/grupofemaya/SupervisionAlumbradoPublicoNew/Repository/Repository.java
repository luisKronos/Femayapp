package com.grupofemaya.SupervisionAlumbradoPublicoNew.Repository;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.Connection.APIClient;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Connection.APIInterface;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Actividad;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Causa;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DamageDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.DeductivasDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.Diagnostico;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.MaterialDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.ReportAlumbDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.User;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.VialidadDTO;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckActividades;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckAdvance;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckCars;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckDeductives;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckPersonal;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckPickups;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCheckTools;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQCuadrilla;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQFinishHour;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQGetPending;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageAfter;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageBefore;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageDuring;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQImageMaterialUsed;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQLogin;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQMaterialUsed;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQNotas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQPendingCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInit;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitThree;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQReportInitTwo;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQRevised;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQRoadsBySector;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQSignContratista;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQSignSupervisor;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.requests.RQStatusCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSFinalQuantification;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSGetListPendings;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSIdCuadrillas;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSInitReport;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSPendingCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSPendingsChecks;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSReportInitOne;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSRoad;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSSector;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSStatusCheck;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.RSSubirCuadrilla;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.responses.rsLogin;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneral;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.DataModels.rsGeneralList;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Constantes;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.LiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static final Repository ourInstance = new Repository();

    private APIInterface apiInterface;
    private RepositoryImp repositoryImp;


    public static   Repository getInstance() {
        return ourInstance;
    }

    private Repository() {
        if (apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }
    }

    public void setRepositoryImp(RepositoryImp repositoryImp) {
        this.repositoryImp = repositoryImp;
    }


    public void requesLogin(String user, String password) {

        RQLogin request = new RQLogin();
        request.setEmail(user);
        request.setPassword(password);
        Call<rsGeneral<rsLogin>> call = apiInterface.requestLogin(request);
        call.enqueue(new Callback<rsGeneral<rsLogin>>() {
            @Override
            public void onResponse(Call<rsGeneral<rsLogin>> call, Response<rsGeneral<rsLogin>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        repositoryImp.succedResponse(response.body().getData());
                    } else {
                        repositoryImp.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    repositoryImp.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<rsLogin>> call, Throwable t) {
                call.cancel();
                repositoryImp.requestFail("Ocurrio un error.");
            }
        });
    }





    public void requestSectors(final RepositoryImp callBack) {
        Call<rsGeneralList<RSSector>> call = apiInterface.requesSectors();
        call.enqueue(new Callback<rsGeneralList<RSSector>>() {
            @Override
            public void onResponse(Call<rsGeneralList<RSSector>> call, Response<rsGeneralList<RSSector>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getData());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<RSSector>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requestRoadsBySector(String idSector, final RepositoryImp callBack) {
        RQRoadsBySector request =  new RQRoadsBySector();
        request.setIdSector(idSector);
        Call<rsGeneralList<RSRoad>> call = apiInterface.requestRoadsBySector(request);
        call.enqueue(new Callback<rsGeneralList<RSRoad>>() {
            @Override
            public void onResponse(Call<rsGeneralList<RSRoad>> call, Response<rsGeneralList<RSRoad>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getData());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<RSRoad>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestInitCheck(RQInitCheck request, final RepositoryImp callBack) {
        Call<rsGeneral<RSInitCheck>> call = apiInterface.requestInitCheck(request);
        call.enqueue(new Callback<rsGeneral<RSInitCheck>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSInitCheck>> call, Response<rsGeneral<RSInitCheck>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getData());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSInitCheck>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }




    public void requestStatusCheck(RQStatusCheck request, final RepositoryImp callBack) {
        Call<rsGeneralList<RSStatusCheck>> call = apiInterface.requestStatusCheck(request);
        call.enqueue(new Callback<rsGeneralList<RSStatusCheck>>() {
            @Override
            public void onResponse(Call<rsGeneralList<RSStatusCheck>> call, Response<rsGeneralList<RSStatusCheck>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getData());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<RSStatusCheck>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requestPendingCheck(RQPendingCheck request, final RepositoryImp callBack) {
        Call<rsGeneral<RSPendingCheck>> call = apiInterface.requestPendingCheck(request);
        call.enqueue(new Callback<rsGeneral<RSPendingCheck>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSPendingCheck>> call, Response<rsGeneral<RSPendingCheck>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getData());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSPendingCheck>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestPendingsChecks(RQPendingCheck request, final RepositoryImp callBack) {
        Call<rsGeneral<RSPendingsChecks>> call = apiInterface.requestPendingsChecks(request);
        call.enqueue(new Callback<rsGeneral<RSPendingsChecks>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSPendingsChecks>> call, Response<rsGeneral<RSPendingsChecks>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getData());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSPendingsChecks>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requestCheckPersonal(RQCheckPersonal request, final RepositoryImp callBack) {
        Call<rsGeneral<RSInitCheck>> call = apiInterface.requestCheckPersonal(request);
        call.enqueue(new Callback<rsGeneral<RSInitCheck>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSInitCheck>> call, Response<rsGeneral<RSInitCheck>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSInitCheck>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestCheckTools(RQCheckTools request, final RepositoryImp callBack) {
        Call<rsGeneral<String>> call = apiInterface.requestNewCheckTools(request);
        call.enqueue(new Callback<rsGeneral<String>>() {
            @Override
            public void onResponse(Call<rsGeneral<String>> call, Response<rsGeneral<String>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<String>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestCheckPickups(RQCheckPickups request, final RepositoryImp callBack) {
        Call<rsGeneral<String>> call = apiInterface.requestnewCheckPickups(request);
        call.enqueue(new Callback<rsGeneral<String>>() {
            @Override
            public void onResponse(Call<rsGeneral<String>> call, Response<rsGeneral<String>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<String>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requestCheckActividades(RQCheckActividades request, final RepositoryImp callBack) {
        Call<rsGeneral<String>> call = apiInterface.requestnewCheckActividades(request);
        call.enqueue(new Callback<rsGeneral<String>>() {
            @Override
            public void onResponse(Call<rsGeneral<String>> call, Response<rsGeneral<String>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<String>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestCheckCars(RQCheckCars request, final RepositoryImp callBack) {
        Call<rsGeneral<String>> call = apiInterface.requestNewCheckCars(request);
        call.enqueue(new Callback<rsGeneral<String>>() {
            @Override
            public void onResponse(Call<rsGeneral<String>> call, Response<rsGeneral<String>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<String>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }




    public void requestCheckDeductives(RQCheckDeductives request, final RepositoryImp callBack) {
        Call<rsGeneral<String>> call = apiInterface.requestNewCheckDeductives(request);
        call.enqueue(new Callback<rsGeneral<String>>() {
            @Override
            public void onResponse(Call<rsGeneral<String>> call, Response<rsGeneral<String>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<String>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestCheckAdvance(RQCheckAdvance request, final RepositoryImp callBack) {
        Call<rsGeneral<String>> call = apiInterface.requestNewCheckAdvance(request);
        call.enqueue(new Callback<rsGeneral<String>>() {
            @Override
            public void onResponse(Call<rsGeneral<String>> call, Response<rsGeneral<String>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<String>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestFinalQuantification(RQStatusCheck request, final RepositoryImp callBack) {
        Call<rsGeneral<RSFinalQuantification>> call = apiInterface.requestFinalQuantification(request);
        call.enqueue(new Callback<rsGeneral<RSFinalQuantification>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSFinalQuantification>> call, Response<rsGeneral<RSFinalQuantification>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSFinalQuantification>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestCheckRevised(RQRevised request, final RepositoryImp callBack) {
        Call<rsGeneral<String>> call = apiInterface.requestCheckRevised(request);
        call.enqueue(new Callback<rsGeneral<String>>() {
            @Override
            public void onResponse(Call<rsGeneral<String>> call, Response<rsGeneral<String>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<String>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requestGetPenalties(final RepositoryImp callBack) {
        Call<rsGeneralList<DeductivasDTO>> call = apiInterface.requestGetPenalties();
        call.enqueue(new Callback<rsGeneralList<DeductivasDTO>>() {
            @Override
            public void onResponse(Call<rsGeneralList<DeductivasDTO>> call, Response<rsGeneralList<DeductivasDTO>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<DeductivasDTO>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestGetVialidades(String idCuadrante, final RepositoryImp callBack) {
        Call<rsGeneralList<VialidadDTO>> call = apiInterface.requestGetVialidades(idCuadrante);
        call.enqueue(new Callback<rsGeneralList<VialidadDTO>>() {
            @Override
            public void onResponse(Call<rsGeneralList<VialidadDTO>> call, Response<rsGeneralList<VialidadDTO>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setVialidades(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<VialidadDTO>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requestInitReport(ReportAlumbDTO request, final RepositoryImp callBack) {
        Call<rsGeneral<RSInitReport>> call = apiInterface.requestReportInit(request);
        call.enqueue(new Callback<rsGeneral<RSInitReport>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSInitReport>> call, Response<rsGeneral<RSInitReport>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        if(LiveData.getInstance().getLiveReport().getIdReportAlumbradoAux() == 0) {
                            LiveData.getInstance().getLiveReport().setIdReportAlumbrado(response.body().getData().getIdReportAlumbrado());
                        }
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSInitReport>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requesReportSecond(ReportAlumbDTO request, final RepositoryImp callBack) {
        Call<rsGeneral<RSInitReport>> call = apiInterface.requestReport2(request);
        call.enqueue(new Callback<rsGeneral<RSInitReport>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSInitReport>> call, Response<rsGeneral<RSInitReport>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        //LiveData.getInstance().getLiveReport().setIdReportAlumbrado(response.body().getData().getIdReportAlumbrado());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSInitReport>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requesReportThird(ReportAlumbDTO request, final RepositoryImp callBack) {
        Call<rsGeneral<RSInitReport>> call = apiInterface.requestReport3(request);
        call.enqueue(new Callback<rsGeneral<RSInitReport>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSInitReport>> call, Response<rsGeneral<RSInitReport>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSInitReport>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }




    public void requestGetDamages(final RepositoryImp callBack) {
        Call<rsGeneralList<DamageDTO>> call = apiInterface.requestGetMDamages();
        call.enqueue(new Callback<rsGeneralList<DamageDTO>>() {
            @Override
            public void onResponse(Call<rsGeneralList<DamageDTO>> call, Response<rsGeneralList<DamageDTO>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setListDamges(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<DamageDTO>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestGetMaterials(final RepositoryImp callBack) {
        Call<rsGeneralList<MaterialDTO>> call = apiInterface.requestGetMaterials();
        call.enqueue(new Callback<rsGeneralList<MaterialDTO>>() {
            @Override
            public void onResponse(Call<rsGeneralList<MaterialDTO>> call, Response<rsGeneralList<MaterialDTO>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setListMaterials(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<MaterialDTO>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }



    public void requestReportDirector(final RepositoryImp callBack) {
        Call<rsGeneral<RSInitReport>> call = apiInterface.requestReportDirector(LiveData.getInstance().getLiveReportDirector());
        call.enqueue(new Callback<rsGeneral<RSInitReport>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSInitReport>> call, Response<rsGeneral<RSInitReport>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSInitReport>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }


    public void requestGetAllVialidades(final RepositoryImp callBack) {
        Call<rsGeneralList<VialidadDTO>> call = apiInterface.requestAllVialidades();
        call.enqueue(new Callback<rsGeneralList<VialidadDTO>>() {
            @Override
            public void onResponse(Call<rsGeneralList<VialidadDTO>> call, Response<rsGeneralList<VialidadDTO>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setVialidades(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<VialidadDTO>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestListPendings(RQGetPending request, final RepositoryImp callBack) {
        Call<rsGeneralList<RSGetListPendings>> call = apiInterface.requestListPendings(request);
        call.enqueue(new Callback<rsGeneralList<RSGetListPendings>>() {
            @Override
            public void onResponse(Call<rsGeneralList<RSGetListPendings>> call, Response<rsGeneralList<RSGetListPendings>> response) {
                if (response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setListPendings(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail(response.body().getHeader().getMessage());
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<RSGetListPendings>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    //NEW LINES
    public void requestCuadrillas(RQCuadrilla request, final RepositoryImp callBack) {
        Call<rsGeneral<RSSubirCuadrilla>> call = apiInterface.requestCuadrillas(request);
        call.enqueue(new Callback<rsGeneral<RSSubirCuadrilla>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSSubirCuadrilla>> call, Response<rsGeneral<RSSubirCuadrilla>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSSubirCuadrilla>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestIdCuadrillas(User idUser, final RepositoryImp callBack) {
        Call<rsGeneralList<RSIdCuadrillas>> call = apiInterface.requestIdCuadrillas(idUser);
        call.enqueue(new Callback<rsGeneralList<RSIdCuadrillas>>() {
            @Override
            public void onResponse(Call<rsGeneralList<RSIdCuadrillas>> call, Response<rsGeneralList<RSIdCuadrillas>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setIdCuadrillas(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<RSIdCuadrillas>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestReportInitOne(RQReportInit request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestReportInitOne(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setResponseReportInit(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestReportInitTwo(RQReportInitTwo request, final RepositoryImp callBack) {
        Call<rsGeneral<String>> call = apiInterface.requestReportInitTwo(request);
        call.enqueue(new Callback<rsGeneral<String>>() {
            @Override
            public void onResponse(Call<rsGeneral<String>> call, Response<rsGeneral<String>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<String>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestImageBefore(RQImageBefore request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestImageBefore(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestImageDuring(RQImageDuring request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestImageDuring(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestImageAfter(RQImageAfter request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestImageAfter(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestReportInitThree(RQReportInitThree request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestReportInitThree(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        RSReportInitOne report = new RSReportInitOne();
                        report.setIdReportAlumbrado(response.body().getData().getIdReportAlumbrado());
                        LiveData.getInstance().getListIdReportAlumbrados().add(report);
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestGetCausas(final RepositoryImp callBack) {
        Call<rsGeneralList<Causa>> call = apiInterface.requestGetCausas();
        call.enqueue(new Callback<rsGeneralList<Causa>>() {
            @Override
            public void onResponse(Call<rsGeneralList<Causa>> call, Response<rsGeneralList<Causa>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setListCausa(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<Causa>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestGetDiagnostico(final RepositoryImp callBack) {
        Call<rsGeneralList<Diagnostico>> call = apiInterface.requestGetDiagnostico();
        call.enqueue(new Callback<rsGeneralList<Diagnostico>>() {
            @Override
            public void onResponse(Call<rsGeneralList<Diagnostico>> call, Response<rsGeneralList<Diagnostico>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setListDiagnostico(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<Diagnostico>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestGetActividades(final RepositoryImp callBack) {
        Call<rsGeneralList<Actividad>> call = apiInterface.requestGetActividades();
        call.enqueue(new Callback<rsGeneralList<Actividad>>() {
            @Override
            public void onResponse(Call<rsGeneralList<Actividad>> call, Response<rsGeneralList<Actividad>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        LiveData.getInstance().setListActividad(response.body().getData());
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneralList<Actividad>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestMaterialesUsed(RQMaterialUsed request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestMaterialesUsed(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestImageMaterialUsed(RQImageMaterialUsed request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestImageMaterialUsed(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestReportNotas(RQNotas request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestReportNotas(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestSignContratista(RQSignContratista request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestSignContratista(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestSignSupervisor(RQSignSupervisor request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestSignSupervisor(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }

    public void requestFinishHour(RQFinishHour request, final RepositoryImp callBack) {
        Call<rsGeneral<RSReportInitOne>> call = apiInterface.requestFinishHour(request);
        call.enqueue(new Callback<rsGeneral<RSReportInitOne>>() {
            @Override
            public void onResponse(Call<rsGeneral<RSReportInitOne>> call, Response<rsGeneral<RSReportInitOne>> response) {
                if(response.body() != null) {
                    if (response.body().getHeader().getCode() == Constantes.CODE_SUCCEED) {
                        callBack.succedResponse(response.body().getHeader().getMessage());
                    } else {
                        callBack.requestFail(response.body().getHeader().getMessage());
                    }
                } else {
                    callBack.requestFail("Ocurrio un error.");
                }
            }

            @Override
            public void onFailure(Call<rsGeneral<RSReportInitOne>> call, Throwable t) {
                call.cancel();
                callBack.requestFail("Ocurrio un error.");
            }
        });
    }
}
