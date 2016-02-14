<?php

    include_once "ControladorUser.php";

    if(isset($_POST['jsonObject'])){
        $jsonObject = json_decode($_POST['jsonObject']);
        $registroId = $jsonObject->usuarioRemetente->registrationId;
        $nick = $jsonObject->usuarioRemetente->nickname;
        $urlFoto = $jsonObject->usuarioRemetente->urlFoto;

        //echo "asdasd ".$jsonObject;
        $controlador = ControladorUser::getInstance();


        echo json_encode(['resposta'=>$controlador->cadastrar($registroId,$nick,$urlFoto)]);
        //echo json_encode($controlador->cadastrar($registroId,$nick));
    }
