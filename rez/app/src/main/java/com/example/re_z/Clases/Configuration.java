package com.example.re_z.Clases;

public class Configuration {
    public static final String URL= "http://192.168.0.31/";
    public static final String ROOT_URL = URL+"android/re-z/";
    public static final String URL_MIS_RECETAS = ROOT_URL+"mis_recetas/";
    public static final String URL_ADD = URL_MIS_RECETAS+"add.php";
    public static final String URL_DELETE = URL_MIS_RECETAS+"delete.php";
    public static final String URL_UPDATE = URL_MIS_RECETAS+"update.php";
    public static final String URL_SELECT = URL_MIS_RECETAS+"select.php";
    public static final String URL_SELECT_FROM_USER = URL_MIS_RECETAS+"select_from_user.php";
    public static final String URL_SELECT_ID = URL_MIS_RECETAS+"select_id.php";


    //Archivos PHP favoritos
    public static final String URL_FAVORITOS = ROOT_URL+"favoritos/";
    public static final String URL_DELETE_Favoritos=URL_FAVORITOS +"delete_favoritos.php";
    public static final String URL_SELECT_Favoritos=URL_FAVORITOS +"select_favoritos.php";
    public static final String URL_ADD_Favoritos=URL_FAVORITOS +"add_favoritos.php";
    public static final String URL_VALIDATION_Favoritos=URL_FAVORITOS +"validacion_favoritos.php";

    //PHP comentarios
    public static final String URL_COMENTARIOS = ROOT_URL+"comentarios/";
    public static final String URL_DELETE_COMENTARIOS = URL_COMENTARIOS+"delete_comentario.php";
    public static final String URL_ADD_COMENTARIOS = URL_COMENTARIOS+"add_comentario.php";
    public static final String URL_EDIT_COMENTARIOS = URL_COMENTARIOS+"edit_comentario.php";
    public static final String URL_SELECT_COMENTARIOS_FROM_USER = URL_COMENTARIOS+"select_comentario_from_user.php";
    public static final String URL_SELECT_COMENTARIOS_FROM_RECETA = URL_COMENTARIOS+"select_comentario_from_receta.php";

    //PHP usuarios
    public static final String URL_LOGIN = ROOT_URL+"login.php";
    public static final String URL_REGISTER = ROOT_URL+"registro.php";
    public static final String URL_PERFIL = ROOT_URL+"usuario/";
    public static final String URL_CONSULTA_PERFIL = URL_PERFIL+"consulta_perfil.php";

}