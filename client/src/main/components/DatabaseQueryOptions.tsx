import { Form, Modal, Radio, Select, Tooltip } from "antd";
import { useEffect, useState } from "react";

const { Option } = Select;

interface DatabaseQueryOptionsProps {
  visible: boolean;
  onCancel: () => void;
  headers: string[];
  showPositions: boolean;
  positionTieBreakerIndex?: number;
  setShowPositions: (showPositions: boolean) => void;
  setPositionTieBreakerIndex: (setPositionTieBreakerIndex?: number) => void;
}

const DatabaseQueryOptions = ({
  visible,
  onCancel,
  headers,
  showPositions,
  positionTieBreakerIndex,
  setShowPositions,
  setPositionTieBreakerIndex,
}: DatabaseQueryOptionsProps) => {
  const [optionsShowPositions, setOptionsShowPositions] = useState(
    showPositions
  );
  const [
    optionsPositionTieBreakerIndex,
    setOptionsPositionTieBreakerIndex,
  ] = useState(positionTieBreakerIndex);

  const handleOk = () => {
    setShowPositions(optionsShowPositions);
    setPositionTieBreakerIndex(optionsPositionTieBreakerIndex);

    // Just close
    onCancel();
  };

  // Resets value to the used on if the user change values, close the modal and opens it again
  useEffect(() => {
    setOptionsShowPositions(showPositions);
    setOptionsPositionTieBreakerIndex(positionTieBreakerIndex);
  }, [visible, showPositions, positionTieBreakerIndex]);

  return (
    <Modal visible={visible} onCancel={onCancel} onOk={handleOk}>
      <Form>
        <Form.Item label={<b>Show positions</b>}>
          <Radio.Group
            onChange={(e) => setOptionsShowPositions(e.target.value)}
            value={optionsShowPositions}
          >
            <Radio value={true}>Yes</Radio>
            <Radio value={false}>No</Radio>
          </Radio.Group>
        </Form.Item>
        {optionsShowPositions && (
          <Form.Item
            label={
              <Tooltip title='In some cases, you may want that instead of the position number, a simble "-" appears intead, to consider a tie. This is the column that will be observed to decide ties'>
                <b>Position tie breaker column</b>
              </Tooltip>
            }
          >
            <Select
              value={optionsPositionTieBreakerIndex}
              onChange={setOptionsPositionTieBreakerIndex}
              allowClear
            >
              {headers.map((header, i) => (
                <Option value={i}>{`${i + 1}. ${header}`}</Option>
              ))}
            </Select>
          </Form.Item>
        )}
      </Form>
    </Modal>
  );
};

export default DatabaseQueryOptions;
