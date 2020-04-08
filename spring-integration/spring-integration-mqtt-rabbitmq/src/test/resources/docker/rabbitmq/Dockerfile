FROM rabbitmq:management
ADD ./plugin/rabbitmq_delayed_message_exchange-20171201-3.7.x.ez $RABBITMQ_HOME/plugins/rabbitmq_delayed_message_exchange-20171201-3.7.x.ez
RUN rabbitmq-plugins enable --offline rabbitmq_management
RUN rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange
RUN rabbitmq-plugins enable --offline rabbitmq_mqtt