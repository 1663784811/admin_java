var KISBPM = KISBPM || {};
KISBPM.URL = {

    getModel: function(modelId) {
        return ACTIVITI.CONFIG.contextRoot + '/model/json/' + modelId;
    },

    getStencilSet: function() {
        return 'editor-app/stencilset.json?version=' + Date.now();
    },

    putModel: function(modelId) {
        return ACTIVITI.CONFIG.contextRoot + '/model/save/' + modelId;
    }
};